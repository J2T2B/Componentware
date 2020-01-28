package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.game.local.GameManagerLocal;

import java.util.List;

public class MockGameManager implements GameManagerLocal {
    @Override
    public List<Game> getGamesForUser(String session) {
        return null;
    }
}
