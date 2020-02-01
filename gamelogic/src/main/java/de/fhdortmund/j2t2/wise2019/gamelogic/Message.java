package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Collection;
import java.util.List;

public interface Message {
    String getId();
    String getText();

    int getDelay();
    boolean isRoot();
    Points getPoints();
    double getProbably();
    List<? extends Answer> getAnswers();
}