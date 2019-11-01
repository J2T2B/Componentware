package de.fhdortmund.j2t2.wise2019.gamelogic;

import org.jetbrains.annotations.*;

public interface Chat {

    Chatpartner getChatpartner();

    void addMessage(Message message);
    @Nullable
    Message getLastMessage();
}
