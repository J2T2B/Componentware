package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.game.local.GameManagerLocal;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatRemoteModel;

import javax.websocket.EncodeException;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/game/{usertoken}")
public class GameEndpoint {

    private Session session;
    private GameManagerLocal gameManager;
    private List<Chat> chats;

    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) throws IOException {
        this.session = session;
        this.chats = gameManager.getChatsForUser(token).stream().map(ChatRemoteModel::new).collect(Collectors.toList());
        reply(chats);
    }

    @OnError
    public void onError(Throwable throwable){

    }

    private void reply(Object replyObject) throws IOException {
        try {
            session.getBasicRemote().sendObject(replyObject);
        } catch (EncodeException e) {
            onError(e);
        }
    }
}
