package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.List;

public interface PlayResult {

    Chat.ChatMessage getMessage();

    boolean isEnd();

    List<PlayResultData> getPlayResultData();
}
