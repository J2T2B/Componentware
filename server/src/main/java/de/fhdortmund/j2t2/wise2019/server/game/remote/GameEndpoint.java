package de.fhdortmund.j2t2.wise2019.server.game.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.ErrorWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.WebSocketCreatedCommand;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatRemoteModel;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.*;
import de.fhdortmund.j2t2.wise2019.server.user.UserManager;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManager;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

@ServerEndpoint(value = "/game/{usertoken}")
public class GameEndpoint {

    private Session session;

    @Inject
    private SessionManager sessionManager;
    @Inject
    private UserManager userManager;
    @Inject
    private RemoteGameManager gameManager;

    private List<Game> games;
    private final Gson gson;
    private String token;

    public GameEndpoint() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AbstractWebSocketCommand.class, new AbstractWebsocketCommandAdapter());
        gson = gsonBuilder.create();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) throws IOException, EncodeException {
        this.session = session;
        this.games = sessionManager.getGamesForToken(token);
        send(new WebSocketCreatedCommand());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {

        AbstractWebSocketCommand command = gson.fromJson(message, AbstractWebSocketCommand.class);
        switch (command.getCommand()){
            case "Reinit":  handleReinitcommand();
            case "SubmitAnswer": {
                SubmitAnswerWebSocketCommand submitCommand = (SubmitAnswerWebSocketCommand) command;
                handleSubmitCommand(submitCommand.getAnswerId(), submitCommand.getChatId());
                break;
            }
            case "ReadMessage": {
                ReadMessageWebSocketCommand readMessageCommand = (ReadMessageWebSocketCommand) command;
                handleReadMessageCommand(readMessageCommand.getMessageId());
                break;
            }
            default: throw new UnsupportedOperationException(command.getCommand());
        }
    }



    @OnError
    public void onError(Throwable throwable){
        try {
            send(new ErrorWebSocketCommand());
        } catch (IOException | EncodeException e) {
            e.printStackTrace();  //TODO error handling
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.close();
        sessionManager.invalidate(token);
    }


    private void handleReinitcommand() throws IOException, EncodeException {
        for(Game game : games){
            GameState<?> gameState = game.getGameState();
            for(Chat chat : gameState.getOpenChats()){
                ChatRemoteModel chatRemoteModel = new ChatRemoteModel(chat);
                send(new CreateChatWebSocketCommand(chatRemoteModel));
            }
        }
    }

    private void handleSubmitCommand(int answerId, int chatId) {
        Game game = gameManager.getGameForRemoteChatId(chatId);

    }

    private void handleReadMessageCommand(String messageId) {
    }


    private void send(AbstractWebSocketCommand replyObject) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(replyObject);
    }

    private void SendCreateChatCommand(){

    }
}
