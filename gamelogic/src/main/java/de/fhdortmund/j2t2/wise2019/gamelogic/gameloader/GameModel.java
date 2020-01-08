package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameModel {
    private List<Chatpartner> chatpartners = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public void addMessages(Collection<? extends Message> messages){
        this.messages.addAll(messages);
    }

    public void addChatpartners(Collection<? extends Chatpartner> chatpartners){
        this.chatpartners.addAll(chatpartners);
    }

    public ArrayList<Message> getMessages(){
        return new ArrayList<>(messages);
    }

    public ArrayList<Chatpartner> getChatpartners(){
        return new ArrayList<>(chatpartners);
    }
}
