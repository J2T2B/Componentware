package de.fhdortmund.j2t2.wise2019.server.game.local;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.GameManager;

import javax.ejb.Local;
import java.util.List;

@Local
public interface GameManagerLocal extends GameManager {
    List<Chat> getChatsForUser(String username);
}
