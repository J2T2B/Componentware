package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

import java.util.ArrayList;
import java.util.List;

public class PlayResultMessage implements PlayResult {

    private List<Chat.ChatMessageImpl> messages;

    public PlayResultMessage(Chat.ChatMessageImpl message) {
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public PlayResultMessage(Chat.ChatMessageImpl... messages) {
        this.messages = new ArrayList<>();
        for(Chat.ChatMessageImpl msg : messages) {
            this.messages.add(msg);
        }
    }

    @Override
    public List<Chat.ChatMessageImpl> getMessages() {
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
