package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.List;

public interface PlayResult {

    Message getMessage();

    boolean isEnd();

    List<PlayResultData> getPlayResultData();
}
