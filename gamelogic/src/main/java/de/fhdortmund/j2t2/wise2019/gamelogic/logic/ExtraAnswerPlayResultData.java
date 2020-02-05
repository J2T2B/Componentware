package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;

import java.util.List;
import java.util.Map;

public class ExtraAnswerPlayResultData implements PlayResultData {

    Map<Long, List<Answer>> extraAnswers;

    public ExtraAnswerPlayResultData(Map<Long, List<Answer>> extraAnswers) {
        this.extraAnswers = extraAnswers;
    }

    public Map<Long, List<Answer>> getExtraAnswers() {
        return extraAnswers;
    }
}
