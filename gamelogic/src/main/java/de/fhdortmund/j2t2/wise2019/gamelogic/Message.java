package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Collection;

public interface Message {
    String getId();
    String getText();
    String getImage();
    Collection<? extends Answer> getAnswers();
}
