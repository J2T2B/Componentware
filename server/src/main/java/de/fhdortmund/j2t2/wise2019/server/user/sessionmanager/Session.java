package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;

import java.util.List;

public class Session {

    private String sessionId;
    private String username;
    private List<GameState<?>> gameStates;

    public Session(String sessionId, String username, List<GameState<?>> gameStates) {
        this.sessionId = sessionId;
        this.username = username;
        this.gameStates = gameStates;
    }

    public List<GameState<?>> getGameStates() {
        return gameStates;
    }

    public void setGameStates(List<GameState<?>> gameStates) {
        this.gameStates = gameStates;
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
}
