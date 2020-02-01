package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.ArrayList;
import java.util.List;

public class PlayResultEnd implements PlayResult {

    private Message message;

    public PlayResultEnd() {

    }

    public PlayResultEnd(Message message) {
        this.message = message;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public List<PlayResultData> getPlayResultData() {
        return new ArrayList<>();
    }
}
