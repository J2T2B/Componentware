package de.fhdortmund.j2t2.wise2019.gamelogic;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import java.util.List;

public interface GameManager {
    List<Game> getGamesForUser(String session);
}
