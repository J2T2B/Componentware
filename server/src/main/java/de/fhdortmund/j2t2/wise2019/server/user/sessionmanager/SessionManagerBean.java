package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.persistence.daos.UserDao;
import de.fhdortmund.j2t2.wise2019.server.user.User;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Dependent
@Singleton
public class SessionManagerBean implements LocalSessionManager, RemoteSessionManager {

    @Inject
    private UserDao userDao;
    private Map<String, Session> sessions = new HashMap<>();

    @Override
    public String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        if(user.getGames().size() == 0){
            user.setGames(createNewGameSessions());
        }

        Session session = new Session(sessionId, user, user.getGames());
        sessions.put(sessionId, session);

        return sessionId;
    }

    @Override
    public List<Game> getGamesForToken(String token) {
        return sessions.get(token).getUser().getGames();
    }

    @Override
    public void invalidate(String token) {
        if(sessions.containsKey(token)) {
            Session session = sessions.get(token);
            userDao.persist(session.getUser());
            session.invalidate();
            sessions.remove(token);
        } else {
            //Nichts zu tun
        }
    }

    @Override
    public Session getSession(String token) {
        return sessions.get(token);
    }

    private List<Game> createNewGameSessions(){
        ServiceLoader<Game> loadedGames = ServiceLoader.load(Game.class);
        List<Game> loaded = new ArrayList<>();
        for(Game game: loadedGames){
            loaded.add(game);
        }
        return loaded;
    }
}
