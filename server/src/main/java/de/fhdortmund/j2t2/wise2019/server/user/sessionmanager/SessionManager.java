package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.user.User;

import java.io.Serializable;
import java.util.List;

public interface SessionManager extends Serializable {
    String createSession(User user);

    List<Game> getGamesForToken(String token);

    void invalidate(String token);

    Session getSession(String token);
}
