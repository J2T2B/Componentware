package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.ErrorWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.WebSocketCreatedCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.*;
import de.fhdortmund.j2t2.wise2019.server.persistence.daos.UserDao;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManager;
import lombok.SneakyThrows;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint(value = "/game/{usertoken}", encoders = MessageCoder.class, decoders = MessageCoder.class)
public class GameEndpoint implements Serializable {
    private static final boolean DEBUG_IGNORE_DELAYS = false;

    private static final int CHAT_CREATION_PERIOD_IN_SECONDS = 15;
    private int currentGameIndex = 0;

    private Session session;

    @Inject
    private UserDao userDao;
    @Inject
    private SessionManager sessionManager;
    @Inject
    private RemoteChatManager chatManager;

    private List<Game> games;
    private String token;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) {
        executorService.scheduleAtFixedRate(this::createNewChat, 1, CHAT_CREATION_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
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
            userDao.persist(sessionManager.getSession(token).getUser());
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

    @SneakyThrows
    @PreDestroy
    public void destroy() {
        onClose(session);
    }


    private void handleReinitcommand() {
        for(Game game : games){
            GameState<?> gameState = game.getGameState();
            for(Chat chat : gameState.getOpenChats()){
                //ChatImpl chatImpl = new ChatImpl(chat);
                sendCreateChatCommand(chat, game);
            }
            if(game.getGameState().getData() instanceof Points){
                sendChangePointsCommand((Points) game.getGameState().getData());
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
                boolean gameOver = pr.isEnd();
                if(game.getGameState().getData() instanceof Points){
                    sendChangePointsCommand((Points) game.getGameState().getData());
                    if(((Points) game.getGameState().getData()).anyZeroOrLower()) {
                        gameOver = true;
                    }
                }
                if(pr.getPlayResultData() instanceof ExtraAnswerPlayResultData) {
                    sendExtraAnswers(game, ((ExtraAnswerPlayResultData) pr.getPlayResultData()).getExtraAnswers().entrySet());
                }
                if(gameOver){
                    sendGameOverCommand();
                }
                break;
            }
        }
    }

    public void sendGameOverCommand() {
        send(new GameOverWebSocketCommand());
        /*userDao.delete(sessionManager.getSession(token).getUser());
        try {
            onClose(session);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
        userDao.persist(sessionManager.getSession(token).getUser());
    }

    public void sendAddMessageCommand(long chatId, ChatMessage chatMessage) {
        AddMessageWebSocketCommand command = new AddMessageWebSocketCommand(chatId, chatMessage);
        SimpleMessage message = chatMessage.getMsg();
        int duration = 0;

        if(message instanceof Message){
            duration = ((Message) message).getDelay();
        }
        if(duration > 0 && !DEBUG_IGNORE_DELAYS){
            new CommandDelayer<>(command, duration, () -> send(command));
        } else {
            send(command);
        }
        userDao.persist(sessionManager.getSession(token).getUser());
    }

    public void sendChangePointsCommand(Points points) {
        send(new ChangePointsWebSocketCommand(points));
        userDao.persist(sessionManager.getSession(token).getUser());
    }

    public void createNewChat(){
        System.out.println("Creating new chat");
        updateGameIndex();
        Game game = games.get(currentGameIndex);
        CreateChatResult result = game.createNewChat();
        if(result == null) {
            return;
        }
        sendCreateChatCommand(result.getChat(), game);
        if(result instanceof ChatCreationWithExtraAnswers) {
            sendExtraAnswers(game, ((ChatCreationWithExtraAnswers) result).getExtaAnswers().entrySet());
        }
        userDao.persist(sessionManager.getSession(token).getUser());
    }

    void updateGameIndex() {
        currentGameIndex++;
        currentGameIndex %= games.size(); // Overflow verhindern
    }

    private void sendExtraAnswers(Game game, Set<Map.Entry<Long, List<Answer>>> entrySet) {
        for (Map.Entry<Long, List<Answer>> entry : entrySet) {
            Chat chat = game.getGameState().getChat(entry.getKey());
            String remoteMsgId = chat.getMessages().get(chat.getMessages().size() - 1).getId();
            for (Answer answer : entry.getValue()) {
                send(new AddAnswerWebSocketCommand(entry.getKey(), remoteMsgId, answer));
            }
        }
    }
}
