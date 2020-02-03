package de.fhdortmund.j2t2.wise2019.server.game.beans;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.game.local.GameManagerLocal;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.inject.Named;
import java.util.*;

@Startup
@Named
@Stateful
public class GameManagerBean implements GameManagerLocal {
    Map<String, List<Game>> games = new HashMap<>();

    @Override
    public List<Game> getGamesForUser(String session) {
        return games.computeIfAbsent(session, key -> loadNewGames());
    }

    private List<Game> loadNewGames() {
        ServiceLoader<Game> loadedGames = ServiceLoader.load(Game.class);
        List<Game> loaded = new ArrayList<>();
        for(Game game : loadedGames) {
            loaded.add(game);
        }
        return loaded;
    }
}
