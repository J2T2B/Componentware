package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResultEnd;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResultMessage;

public class DetectivGame implements Game {

    GameModel model = new GameModel();
    public DetectivGame() throws GameLoadingException {
        GameLoader gameLoader = new GameLoader(DetectivGame.class.getClassLoader().getResource("gameDefinition.json").getPath());
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
            for(Message target : answer.getTargets()) {
                if(random >= sum && random < (sum += target.getProbably())) {
                    msg = target;
                }
            }
            res = new PlayResultMessage(msg);
        }

        return res;
    }
}
