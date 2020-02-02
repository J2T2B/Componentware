package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import java.util.List;

/**
 * Represents a user
 */
public interface User {
    String getName();
    String getHash();
    List<Game> getGames();
}
