package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatRemoteModel;

import javax.ejb.Singleton;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SessionScoped
@Singleton
@Named
public class RemoteChatManager implements Serializable {

    private Map<Long, Game> chats = new HashMap<>();

    public Game getGameForRemoteChatId(long chatId){
        return chats.get(chatId);
    }

    public void registerChat(ChatRemoteModel chat, Game game){
        chats.put(chat.getId(), game);
    }
}
