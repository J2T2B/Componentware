package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

import javax.ejb.Singleton;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Singleton
@Named
public class RemoteGameManager implements Serializable {

    public void registerGame(Game game){
        //TODO
    }

    public Game getGameForRemoteChatId(long chatId){
        return null; //TODO
    }


}
