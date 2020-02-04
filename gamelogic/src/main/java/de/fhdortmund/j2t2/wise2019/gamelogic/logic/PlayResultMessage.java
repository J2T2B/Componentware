package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class PlayResultMessage implements PlayResult {

    private List<ChatMessage> messages;

    public PlayResultMessage(ChatMessage message) {
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public PlayResultMessage(ChatMessage... messages) {
        this.messages = new ArrayList<>();
        for(ChatMessage msg : messages) {
            this.messages.add(msg);
        }
    }

    @Override
    public List<ChatMessage> getMessages() {
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
