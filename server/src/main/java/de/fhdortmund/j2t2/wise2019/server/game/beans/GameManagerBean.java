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
import java.util.ArrayList;
import java.util.List;

@Startup
@Stateful
public class GameManagerBean implements GameManagerLocal {
    List<Game> games;
    @PostConstruct
    private void loadGames(){
        Reflections gameScanner = new Reflections(Game.class, new SubTypesScanner());    }
    @Override
    public List<Chat> getChatsForUser(String token) {
        return new ArrayList<>();
    }
}
