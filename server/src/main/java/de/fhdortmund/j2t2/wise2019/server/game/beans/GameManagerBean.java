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
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Startup
@Named
@Stateful
public class GameManagerBean implements GameManagerLocal {
    List<Game> games;
    @PostConstruct
    private void loadGames(){
        ServiceLoader<Game> loadedGames = ServiceLoader.load(Game.class);
        this.games = new ArrayList<>();
        for(Game game : loadedGames) {
            this.games.add(game);
        }
    }

    @Override
    public List<Chat> getChatsForUser(String token) {
        return new ArrayList<>();
    }
}
