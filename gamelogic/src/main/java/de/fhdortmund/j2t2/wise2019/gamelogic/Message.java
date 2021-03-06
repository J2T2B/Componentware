package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.List;

public interface Message extends SimpleMessage{
    String getId();
    int getDelay();
    boolean isRoot();
    Points getPoints();
    double getProbably();
    List<? extends Answer> getAnswers();

    default Answer firstAnswer() {
        return getAnswers().get(0);
    }
}