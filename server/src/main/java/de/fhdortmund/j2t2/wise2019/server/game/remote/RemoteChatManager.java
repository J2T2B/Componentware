package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import java.io.Serializable;

public interface RemoteChatManager extends Serializable {
    Game getGameForRemoteChatId(long chatId);
    void registerChat(Chat chat, Game game);
}
