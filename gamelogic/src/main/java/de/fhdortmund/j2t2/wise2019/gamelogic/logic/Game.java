package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

public interface Game {

    /**
     * Play a answer
     * @param answer the answer to play
     * @return the result of the answer
     */
    PlayResult playAnswer(Chat chat, Answer answer);

    CreateChatResult createNewChat();

    GameState<?> getGameState();

    void setGameState(GameState<?> gameState);

    Message getMessageById(String messageId);

    void updateGameModel();
}
