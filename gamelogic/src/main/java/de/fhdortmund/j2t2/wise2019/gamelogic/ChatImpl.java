package de.fhdortmund.j2t2.wise2019.gamelogic;

import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ChatImpl implements Chat, Serializable {
    final long id;

    private ChatPartner chatPartner;
    private List<ChatMessage> messages;

    public ChatImpl(){
        this(new ChatPartnerImpl());
    }

    public ChatImpl(Chat chat) {
        this(chat.getId());
        chatPartner = new ChatPartnerImpl(chat.getChatpartner());
        messages = chat.getMessages();
    }

    public ChatImpl(ChatPartner chatpartner){
        this(RandomUtils.nextLong());
        this.chatPartner = chatpartner;
        messages = new ArrayList<>();
    }

    public ChatImpl(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public ChatPartner getChatpartner() {
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

    public ChatPartner getChatPartner() {
        return chatPartner;
    }

    public void setChatPartner(ChatPartner chatPartner) {
        this.chatPartner = chatPartner;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
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
