package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

import java.util.List;

public class Session {

    private String sessionId;
    private String username;
    private List<Chat> chats;

    public Session(String sessionId, String username, List<Chat> chats) {
        this.sessionId = sessionId;
        this.username = username;
        this.chats = chats;
    }
}
