package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import java.util.List;

public interface GameDao {
    List<Game> getGamesForUser(String username);
}
