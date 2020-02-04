package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class PlayResultEnd implements PlayResult {

    private List<ChatMessage> messages;

    public PlayResultEnd() {
this.messages = new ArrayList<>();
    }

    public PlayResultEnd(ChatMessage message) {
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    @Override
    public List<ChatMessage> getMessages() {
        return messages;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public PlayResultData getPlayResultData() {
        return null;
    }
}
