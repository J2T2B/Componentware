package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Dependent
@Named
public class RemoteChatManagerBean implements RemoteChatManagerLocal, RemoteChatManagerRemote {

    private Map<Long, Game> chats = new HashMap<>();

    public Game getGameForRemoteChatId(long chatId){
        return chats.get(chatId);
    }

    public void registerChat(Chat chat, Game game){
        chats.put(chat.getId(), game);
    }
}
