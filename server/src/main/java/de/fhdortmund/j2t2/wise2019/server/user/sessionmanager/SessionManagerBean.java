package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.game.local.GameManagerLocal;
import de.fhdortmund.j2t2.wise2019.server.user.User;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Named
@Dependent
@Singleton
public class SessionManagerBean implements LocalSessionManager, RemoteSessionManager {

    @Inject
    private GameManagerLocal gameManager;
    private Map<String, Session> sessions = new HashMap<>();

    @Override
    public String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();

        Session session = new Session(sessionId, user.getName(), gameManager.getGamesForUser(user.getName()));
        sessions.put(sessionId, session);

        return sessionId;
    }

    @Override
    public List<Game> getGamesForToken(String token) {
        return gameManager.getGamesForUser(sessions.get(token).getUsername());
    }

    @Override
    public void invalidate(String token) {
        Session session = sessions.get(token);
        session.invalidate();
        sessions.remove(token);
    }
}
