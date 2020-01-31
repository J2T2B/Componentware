package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Collection;

public interface Message extends SimpleMessage{
    String getId();
    String getImage();

    int getDelay();
    boolean isRoot();
    Points getPoints();
    double getProbably();
    Collection<? extends Answer> getAnswers();
}