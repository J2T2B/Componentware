package de.fhdortmund.j2t2.wise2019.server.game.beans;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.game.local.GameManagerLocal;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.List;

@Startup
@Singleton
public class GameManagerBean implements GameManagerLocal {
    
    @Override
    public List<Chat> getChatsForUser(String token) {
        return null;
    }
}
