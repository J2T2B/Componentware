package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

public class SimpleChatCreation implements CreateChatResult {

    private Chat chat;

    public SimpleChatCreation(Chat chat) {
        this.chat = chat;
    }

    @Override
    public Chat getChat() {
        return this.chat;
    }
}
