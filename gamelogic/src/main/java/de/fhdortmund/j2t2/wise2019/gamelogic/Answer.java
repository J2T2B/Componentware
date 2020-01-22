package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.List;

public interface Answer {
    Message getParent();
    int getId();
    String getText();
    List<Message> getTargets();
}
