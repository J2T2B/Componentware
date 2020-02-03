package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.ErrorWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.WebSocketCreatedCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.*;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManager;

import javax.ejb.Schedule;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

@SessionScoped
@ServerEndpoint(value = "/game/{usertoken}", encoders = MessageCoder.class, decoders = MessageCoder.class)
public class GameEndpoint {
    private static final String CHAT_CREATION_PERIOD_IN_SECONDS = "30";
    private int currentGameIndex = 0;

    private Session session;

    @Inject
    private SessionManager sessionManager;
    @Inject
    private RemoteChatManager chatManager;

    private List<Game> games;
    private String token;

    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) {
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

    private void handleSubmitCommand(long answerId, String messageId, long chatId) {
        Game game = chatManager.getGameForRemoteChatId(chatId);
        Chat chat = game.getGameState().getChat(chatId);
        Chat.ChatMessage chatMessage = chat.getMessage(messageId);
        Message msg = (Message) chatMessage.getMsg();

        for(Answer answer : msg.getAnswers()) {
            if (answer.getId() == answerId) {
                List<Chat.ChatMessage> messages = chat.getMessages();
                sendAddMessageCommand(chatId, messages.get(messages.size()-1));
                Object gameData = game.getGameState().getData();
                if(gameData instanceof Points){
                    sendChangePointsCommand((Points) gameData);
                }
            }
        }
    }

    private void handleReadMessageCommand(String messageId, long chatId) {
        Game game = chatManager.getGameForRemoteChatId(chatId);
        Chat chat = game.getGameState().getChat(chatId);
        Chat.ChatMessage message = chat.getMessage(messageId);
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

    public void sendAddMessageCommand(long chatId, Chat.ChatMessage chatMessage) {
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

    @Schedule(second = CHAT_CREATION_PERIOD_IN_SECONDS)
    public void createNewChat(){
        Game game = games.get(currentGameIndex);
        Chat chat = game.createNewChat();
        updateGameIndex();
        sendCreateChatCommand(chat, game);
    }

    void updateGameIndex(){
        currentGameIndex++;
        currentGameIndex %= games.size(); //Overflow verhindern
    }

}
