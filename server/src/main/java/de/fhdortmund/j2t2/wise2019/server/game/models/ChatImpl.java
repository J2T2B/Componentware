package de.fhdortmund.j2t2.wise2019.server.game.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


public class ChatImpl implements Chat, Serializable {
    final long id;

    private ChatpartnerImpl chatPartner;
    private List<ChatMessage> messages;

    public ChatImpl(){
        id = RandomUtils.nextLong(); //TODO
        chatPartner = new ChatpartnerImpl();
        messages = new ArrayList<>();
    }

    public ChatImpl(Chat chat) {
        id = chat.getId();
        chatPartner = new ChatpartnerImpl(chat.getChatpartner());
        messages = chat.getMessages();
    }

    public long getId() {
        return id;
    }

    @Override
    public Chatpartner getChatpartner() {
        return chatPartner;
    }

    @Override
    public void addMessage(Message message, boolean isAnswer) {
        messages.add(new ChatMessage(message, System.currentTimeMillis(), isAnswer));
    }

    @Override
    public List<ChatMessage> getMessages() {
        return messages;
    }

    @Override
    public ChatMessage getMessage(String messageId) {
        return messages.stream().filter(m -> m.getId().equals(messageId)).findAny().orElseThrow(NoSuchElementException::new);
    }
}
