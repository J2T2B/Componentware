package de.fhdortmund.j2t2.wise2019.gamelogic;

public interface Chat {

    Chatpartner getChatpartner();

    void addMessage(Message message);
    Message getLastMessage();
}
