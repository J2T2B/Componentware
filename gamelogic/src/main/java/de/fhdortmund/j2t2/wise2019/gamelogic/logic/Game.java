package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.List;

public interface Game {

    /**
     * Play a answer
     * @param answer the answer to play
     * @return the result of the answer
     */
    PlayResult playAnswer(Answer answer);

    PlayResult playAnswer(int answerId);

    Chat createNewChat();

    GameState<?> getGameState();
}
