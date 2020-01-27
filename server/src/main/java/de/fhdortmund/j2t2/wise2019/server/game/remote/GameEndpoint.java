package de.fhdortmund.j2t2.wise2019.server.game.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.ErrorWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.WebSocketCreatedCommand;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatRemoteModel;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.AbstractWebsocketCommandAdapter;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.CreateChatWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.ReadMessageWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.SubmitAnswerWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.user.UserManagerLocal;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManager;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/game/{usertoken}")
public class GameEndpoint {

    private Session session;
    @Inject
    private SessionManager sessionManager;
    @Inject
    private UserManagerLocal userManager;
    private Map<String, Chat> chats = new HashMap<>();
    private final Gson gson;

    public GameEndpoint() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AbstractWebSocketCommand.class, new AbstractWebsocketCommandAdapter());
        gson = gsonBuilder.create();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) throws IOException, EncodeException {
        this.session = session;
        this.chats = sessionManager.getChatsForToken(token).stream().map(ChatRemoteModel::new).collect(Collectors.toMap(ChatRemoteModel::getId, it -> it));
        reply(new WebSocketCreatedCommand());
    }

    @OnMessage
    public void onMessage(String message, Session session){

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
            reply(new ErrorWebSocketCommand());
        } catch (IOException | EncodeException e) {
            e.printStackTrace();  //TODO error handling
        }
    }

    @OnClose
    public void onClose(Session session){
        throw new UnsupportedOperationException("OnClose"); //TODO
    }

    private void reply(AbstractWebSocketCommand replyObject) throws IOException, EncodeException {
            session.getBasicRemote().sendObject(replyObject);
    }

    private void handleReinitcommand() {
        for(Chat chat : chats.values()){
            try {
                reply(new CreateChatWebSocketCommand(chat));
            } catch (IOException | EncodeException e) {
                onError(e);
            }
        }
    }

    private void handleSubmitCommand(int answerId, int chatId) {

    }

    private void handleReadMessageCommand(String messageId) {
    }
}
