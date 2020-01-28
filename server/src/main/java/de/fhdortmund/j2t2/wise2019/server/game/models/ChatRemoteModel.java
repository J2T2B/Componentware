package de.fhdortmund.j2t2.wise2019.server.game.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class ChatRemoteModel implements Chat, Serializable {

    private static long nextChatModelId = Long.MIN_VALUE;

    final long id = nextChatModelId++;

    private ChatpartnerRemoteModel chatPartner;
    private List<Message> messages;

    public ChatRemoteModel(Chat chat) {
        chatPartner = new ChatpartnerRemoteModel(chat.getChatpartner());
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
    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }
}
