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
    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    @Override
    public List<ChatMessage> getMessages() {
        return messages;
    }

    @Override
    public ChatMessage getMessage(String messageId) {
        return messages.stream().filter(m -> m.getId().equals(messageId)).findAny().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatImpl chat = (ChatImpl) o;

        if (id != chat.id) return false;
        if (!chatPartner.equals(chat.chatPartner)) return false;
        return messages.equals(chat.messages);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + chatPartner.hashCode();
        result = 31 * result + messages.hashCode();
        return result;
    }
}
