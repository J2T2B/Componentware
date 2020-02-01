package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.ArrayList;
import java.util.List;

public class DetectiveGameChat implements Chat {

    private long id;
    private Chatpartner chatpartner;
    private List<ChatMessage> messages;

    public DetectiveGameChat(long id, Chatpartner chatpartner) {
        this.id = id;
        this.chatpartner = chatpartner;
        this.messages = new ArrayList<>();
    }

    public DetectiveGameChat(long id, Chatpartner chatpartner, List<ChatMessage> messages) {
        this.id = id;
        this.chatpartner = chatpartner;
        this.messages = messages;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Chatpartner getChatpartner() {
        return chatpartner;
    }

    @Override
    public void addMessage(Message message, boolean isAnswer) {
        messages.add(new ChatMessage(message, System.currentTimeMillis(), isAnswer));
    }

    @Override
    public List<ChatMessage> getMessages() {
        return null;
    }

    @Override
    public ChatMessage getMessage(String messageId) {
        return null;
    }
}
