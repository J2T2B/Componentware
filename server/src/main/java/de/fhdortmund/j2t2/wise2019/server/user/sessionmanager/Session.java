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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
