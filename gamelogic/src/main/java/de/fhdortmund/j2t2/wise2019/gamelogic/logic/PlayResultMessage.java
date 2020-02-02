package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

public class PlayResultMessage implements PlayResult {

    private Chat.ChatMessage message;

    public PlayResultMessage(Chat.ChatMessage message) {
        this.message = message;
    }

    @Override
    public Chat.ChatMessage getMessage() {
        return message;
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
