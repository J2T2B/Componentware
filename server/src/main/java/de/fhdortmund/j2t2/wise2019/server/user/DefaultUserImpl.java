package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import java.util.ArrayList;
import java.util.List;

public class DefaultUserImpl implements User {
    private final String name;
    private final String hash;

    DefaultUserImpl(String name, String hash){
        this.name = name;
        this.hash = hash;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public List<Game> getGames() {
        return new ArrayList<>();
    }
}
