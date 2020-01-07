package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.ErrorWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.WebSocketCreatedCommand;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatRemoteModel;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.user.UserManagerBean;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/api/game/{usertoken}")
public class GameEndpoint {

    private Session session;
    @Inject
    private UserManagerBean userManager;
    private List<Chat> chats;

    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) throws IOException, EncodeException {
        this.session = session;
        this.chats = userManager.getChatsForUser(token).stream().map(ChatRemoteModel::new).collect(Collectors.toList());
        reply(new WebSocketCreatedCommand());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println(message);
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
}
