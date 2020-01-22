package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Collection;

public interface Answer {
    Message getParent();
    int getId();
    String getText();
    Collection<Message> getTargets();
}
