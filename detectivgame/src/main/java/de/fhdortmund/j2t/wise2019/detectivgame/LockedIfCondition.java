package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;

public class LockedIfCondition {

    private String condition;
    private Object data;

    boolean isLocked(Answer answer) {
        if(condition.equals("notSingleChild")) {
            return answer.getParent().getAnswers().size() > 1;
        } else if(condition.equals("targetAnswerCountSmallerThan")) {
            return answer.firstTarget().getAnswers().size() <  Double.parseDouble(data + "");
        }
        return true;
    }
}