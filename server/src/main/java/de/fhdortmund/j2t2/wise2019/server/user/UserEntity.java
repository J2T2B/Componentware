package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user")
@ApplicationScoped
public class UserEntity implements User{
    @Id
    private String username;
    private String hash;
    @OneToMany
    private List<Game> games;

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public List<Game> getGames() {
        return games;
    }


}
