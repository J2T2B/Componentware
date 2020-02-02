package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

public interface PlayResult {

    Chat.ChatMessage getMessage();

    boolean isEnd();

    PlayResultData getPlayResultData();
}
