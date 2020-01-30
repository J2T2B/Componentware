package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class AbstractGame<T> implements Game {
    protected GameModel gameModel;
    protected GameState<T> gameState;

    protected AbstractGame(ClassLoader gameClassLoader) throws GameLoadingException {
        URL gameDefinitionURL = gameClassLoader.getResource("gameDefinition.json");
        try {
            InputStream gameDefinition = gameDefinitionURL.openStream();
        } catch (NullPointerException | IOException e) {
            throw new GameLoadingException("Unable to load "+ gameDefinitionURL.toString(), e);
        }
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
                    //TODO break hier hin ?
                }
            }
            res = new PlayResultMessage(msg);
        }
        return res;
    }

    @Override
    public PlayResult playAnswer(int answerId) {
        throw new UnsupportedOperationException("TODO"); //TODO
    }

    @Override
    public GameState<T> getGameState() {
        return gameState;
    }

    @Override
    public Chat createNewChat() {
        throw new UnsupportedOperationException("TODO"); //TODO
    }
}
