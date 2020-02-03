package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;

public class LockedIfCondition {

    private String condition;
    private String evalTargetId;
    private Object data;

    boolean isLocked(GameModel model, Answer answer) {
        if(condition.equals("evalTargetAnswerCountSmallerThan")) {
            return model.getMessage(evalTargetId).getAnswers().size() <  Double.parseDouble(data + "");
        } else if(condition.equals("evalTargetAnswerCountGreaterThan")) {
            return model.getMessage(evalTargetId).getAnswers().size() >  Double.parseDouble(data + "");
        }
        return true;
    }
}