package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.List;

public interface Chat {

    Chatpartner getChatpartner();

    void addMessage(Message message);
    List<Message> getMessages();
}
