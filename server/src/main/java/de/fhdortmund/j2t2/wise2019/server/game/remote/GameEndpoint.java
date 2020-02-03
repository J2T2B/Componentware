package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.ErrorWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.WebSocketCreatedCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.*;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint(value = "/game/{usertoken}", encoders = MessageCoder.class, decoders = MessageCoder.class)
public class GameEndpoint implements Serializable {
    private static final int CHAT_CREATION_PERIOD_IN_SECONDS = 45;
    private int currentGameIndex = 0;

    private Session session;

    @Inject
    private SessionManager sessionManager;
    @Inject
    private RemoteChatManager chatManager;

    private List<Game> games;
    private String token;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) {
        executorService.scheduleAtFixedRate(this::createNewChat, CHAT_CREATION_PERIOD_IN_SECONDS, CHAT_CREATION_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
        System.out.println("Open session with id: " +session.getId());
        this.session = session;
        this.games = sessionManager.getGamesForToken(token);
        this.token = token;
        send(new WebSocketCreatedCommand());
    }

    @OnMessage
    public void onMessage(AbstractWebSocketCommand command, Session session) {
        System.out.println("Empfangen: "+command.toString() + "for session id: "+session.getId());
            switch (command.getCommand()) {
                case "Reinit":
                    handleReinitcommand();
                    break;
                case "SubmitAnswer": {
                    SubmitAnswerWebSocketCommand submitCommand = (SubmitAnswerWebSocketCommand) command;
                    handleSubmitCommand(submitCommand.getAnswerId(),submitCommand.getMessageId(), submitCommand.getChatId() );
                    break;
                }
                case "ReadMessage": {
                    ReadMessageWebSocketCommand readMessageCommand = (ReadMessageWebSocketCommand) command;
                    handleReadMessageCommand(readMessageCommand.getMessageId(), readMessageCommand.getChatId());
                    break;
                }
                default:
                    throw new UnsupportedOperationException(command.getCommand());
            }
    }



    @OnError
    public void onError(Throwable throwable){
        System.err.println("Error in WebSocket!");
        throwable.printStackTrace();
        send(new ErrorWebSocketCommand(new Exception(throwable).getMessage()));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("Closing session with id: " + session.getId());
        executorService.shutdown();
        sessionManager.invalidate(token);
        session.close();
    }


    private void handleReinitcommand() {
        for(Game game : games){
            GameState<?> gameState = game.getGameState();
            for(Chat chat : gameState.getOpenChats()){
                //ChatImpl chatImpl = new ChatImpl(chat);
                sendCreateChatCommand(chat, game);
            }
        }
    }

    private void handleSubmitCommand(long answerId, String remoteMessageId, long chatId) {
        Game game = chatManager.getGameForRemoteChatId(chatId);
        Chat chat = game.getGameState().getChat(chatId);
        ChatMessage chatMessage = chat.getMessage(remoteMessageId);
        Message lastMessage = (Message) chatMessage.getMsg();

        for(Answer answer : lastMessage.getAnswers()) {
            if (answer.getId() == answerId) {
                PlayResult pr = game.playAnswer(chat, answer);
                for(ChatMessage msg : pr.getMessages()) {
                    sendAddMessageCommand(chatId, msg);
                }
                Object gameData = pr.getPlayResultData();
                if(gameData instanceof Points){
                    sendChangePointsCommand((Points) gameData);
                }
                if(pr.isEnd()){
                    sendGameOverCommand();
                }
            }
        }
    }

    public void sendGameOverCommand() {
        send(new GameOverWebSocketCommand());
    }

    private void handleReadMessageCommand(String messageId, long chatId) {
        Game game = chatManager.getGameForRemoteChatId(chatId);
        Chat chat = game.getGameState().getChat(chatId);
        ChatMessage message = chat.getMessage(messageId);
        message.setRead(true);
    }


    private void send(AbstractWebSocketCommand replyObject) {
        try {
            session.getBasicRemote().sendObject(replyObject);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    public void sendCreateChatCommand(Chat chat, Game game) {
        chatManager.registerChat(chat, game);
        send(new CreateChatWebSocketCommand(chat));
    }

    public void sendAddMessageCommand(long chatId, ChatMessage chatMessage) {
        AddMessageWebSocketCommand command = new AddMessageWebSocketCommand(chatId, chatMessage);
        SimpleMessage message = chatMessage.getMsg();
        int duration = 0;

        if(message instanceof Message){
            duration = ((Message) message).getDelay();
        }
        if(duration > 0){
            new CommandDelayer<>(command, duration, () -> send(command));
        } else {
            send(command);
        }
    }

    public void sendChangePointsCommand(Points points) {
        send(new ChangePointsWebSocketCommand(points));
    }

    public void createNewChat(){
        System.out.println("Creating new chat");
        Game game = games.get(currentGameIndex);
        CreateChatResult result = game.createNewChat();
        if(result == null) {
            return;
        }
        updateGameIndex();
        sendCreateChatCommand(result.getChat(), game);
        if(result instanceof ChatCreationWithExtraAnswers) {
            for(Map.Entry<Long, List<Answer>> entry : ((ChatCreationWithExtraAnswers) result).getExtaAnswers().entrySet()) {
                Chat chat = game.getGameState().getChat(entry.getKey());
                String remoteMsgId = chat.getMessages().get(chat.getMessages().size() - 1).getId();
                for(Answer answer : entry.getValue()) {
                    send(new AddAnswerWebSocketCommand(entry.getKey(), remoteMsgId, answer));
                }
            }
        }
    }

    void updateGameIndex() {
        currentGameIndex++;
        currentGameIndex %= games.size(); // Overflow verhindern
    }

}
