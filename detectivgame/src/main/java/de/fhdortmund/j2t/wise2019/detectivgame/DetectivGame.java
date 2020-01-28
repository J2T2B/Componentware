package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DetectivGame implements Game {

    GameModel model = new GameModel();
    /**
     * Keine weiteren Daten zu speichern -> Void
     */
    GameState<Void> state = new GameState<>();
    public DetectivGame() throws GameLoadingException {
        InputStream gameDefinition;
        try {
            gameDefinition = DetectivGame.class.getClassLoader().getResource("gameDefinition.json").openStream();
        } catch (IOException | NullPointerException e) {
            throw new GameLoadingException("Unable to load gameDefinition.json", e);
        }
        GameLoader gameLoader = new GameLoader(gameDefinition);
        model.addMessages(gameLoader.loadGame());
    }
    @Override
    public PlayResult playAnswer(Answer answer) {
        PlayResult res;

        if(answer.getTargets().size() < 1) {
            res = new PlayResultEnd();
        } else if(answer.getTargets().size() == 1) {
            Message target = answer.getTargets().get(0);
            if(target.getAnswers().size() == 0) {
                res = new PlayResultEnd(target);
            } else {
                res = new PlayResultMessage(target);
            }
        } else {
            double random = Math.random();
            double sum = 0;
            Message msg = answer.getTargets().get(0);
            for (Message target : answer.getTargets()) {
                if (random >= sum && random < (sum += target.getProbably())) {
                    msg = target;
                }
            }
            res = new PlayResultMessage(msg);
        }
        return res;
    }

    @Override
    public PlayResult playAnswer(int answerId) {
        return null;
    }

    @Override
    public GameState<Void> getGameState() {
        return state;
    }
}
