package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.ChatMessage;
import de.fhdortmund.j2t2.wise2019.gamelogic.ChatPartner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DetectiveGameChat implements Chat {

    private long id;
    private ChatPartner chatpartner;
    private List<ChatMessage> messages;

    public DetectiveGameChat(long id, ChatPartner chatpartner) {
        this.id = id;
        this.chatpartner = chatpartner;
        this.messages = new ArrayList<>();
    }

    public DetectiveGameChat(long id, ChatPartner chatpartner, List<ChatMessage> messages) {
        this.id = id;
        this.chatpartner = chatpartner;
        this.messages = messages;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public ChatPartner getChatpartner() {
        return chatpartner;
    }

    @Override
    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    @Override
    public List<ChatMessage> getMessages() {
        return messages;
    }

    @Override
    public ChatMessage getMessage(String messageId) {
        return messages.stream().filter(m -> messageId.equals(m.getId())).findAny().orElseThrow(NoSuchElementException::new);
    }
}
