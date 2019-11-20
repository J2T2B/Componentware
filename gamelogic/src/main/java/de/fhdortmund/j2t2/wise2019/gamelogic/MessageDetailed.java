package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Collection;

public interface MessageDetailed extends Message {
    int getDelay();
    boolean isRoot();
    Points getPoints();
    double getProbably();
    Collection<? extends AnswerDetailed> getAnswers();
}
