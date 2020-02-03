package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.ArrayList;
import java.util.List;

public class PlayResultMessage implements PlayResult {

    private List<Chat.ChatMessage> messages;

    public PlayResultMessage(Chat.ChatMessage message) {
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public PlayResultMessage(Chat.ChatMessage... messages) {
        this.messages = new ArrayList<>();
        for(Chat.ChatMessage msg : messages) {
            this.messages.add(msg);
        }
    }

    @Override
    public List<Chat.ChatMessage> getMessages() {
        return messages;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public PlayResultData getPlayResultData() {
        return null;
    }
}
