package de.fhdortmund.j2t2.wise2019.server.game.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.io.Serializable;


public class ChatRemoteModel implements Chat, Serializable {

    ChatpartnerRemoteModel chatPartner;
    public ChatRemoteModel(Chat chatForUser) {
    }

    @Override
    public Chatpartner getChatpartner() {
        return chatPartner;
    }

    @Override
    public void addMessage(Message message) {

    }

    @Override
    public Message getLastMessage() {
        return null;
    }
}
