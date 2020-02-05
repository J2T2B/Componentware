package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DefaultUserImpl implements User, Serializable {
    private final String name;
    private final String hash;
    private List<Game> games;

    public DefaultUserImpl(String name, String hash, List<Game> games){
        this.name = name;
        this.hash = hash;
        this.games = games;
    }
}
