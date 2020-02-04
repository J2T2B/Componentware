package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.ChatPartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;

public class GameModel {
    private List<ChatPartner> chatPartners = new ArrayList<>();
    private Map<String, Message> messages = new HashMap<>();

    public void addMessages(Map<String, ? extends Message> messages){
        this.messages.putAll(messages);
    }

    public void addChatpartners(Collection<? extends ChatPartner> chatpartners){
        this.chatPartners.addAll(chatpartners);
    }

    public Map<String, Message> getMessages(){
        return new HashMap<>(messages);
    }

    public Message getMessage(String id) {
        return messages.get(id);
    }

    public ArrayList<ChatPartner> getChatPartners(){
        return new ArrayList<>(chatPartners);
    }

    public Message getSomeRootMessage(){
        List<Message> rootMessages = messages.values().stream().filter(Message::isRoot).collect(Collectors.toList());
        return rootMessages.get(RandomUtils.nextInt(0, rootMessages.size()));
    }
}
