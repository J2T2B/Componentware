package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;

public class SupportGame implements Game {
    private GameState<Points> gameState;
    private GameModel gameModel;

    public SupportGame(){

    }

    @Override
    public PlayResult playAnswer(Answer answer) {
        return null;
    }

    @Override
    public PlayResult playAnswer(int answerId) {
        return null;
    }

    @Override
    public Chat createNewChat() {

        return null;
    }

    @Override
    public GameState<Points> getGameState() {
        return null;
    }
}
