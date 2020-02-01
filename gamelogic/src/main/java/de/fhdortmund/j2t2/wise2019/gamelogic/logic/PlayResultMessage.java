package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.ArrayList;
import java.util.List;

public class PlayResultMessage implements PlayResult {

    private Message message;

    public PlayResultMessage(Message message) {
        this.message = message;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public List<PlayResultData> getPlayResultData() {
        return new ArrayList<>();
    }
}
