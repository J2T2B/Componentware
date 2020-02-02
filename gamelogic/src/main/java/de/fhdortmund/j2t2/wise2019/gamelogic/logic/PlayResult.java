package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

import java.util.List;

public interface PlayResult {

    List<Chat.ChatMessage> getMessages();

    boolean isEnd();

    PlayResultData getPlayResultData();
}
