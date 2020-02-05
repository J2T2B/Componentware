package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.user.User;
import lombok.Data;

import java.util.List;

@Data
public class Session {

    private String sessionId;
    private User user;
    private List<Game> games;

    public Session(String sessionId, User user, List<Game> games) {
        this.sessionId = sessionId;
        this.user = user;
        this.games = games;
    }

    public void invalidate() {
        //speichere Daten. Aber derzeit nichts zu speichern
    }
}
