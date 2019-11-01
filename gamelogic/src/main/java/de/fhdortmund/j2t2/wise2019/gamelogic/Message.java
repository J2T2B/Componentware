package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Collection;

public interface Message {
    String getId();
    String getTexT();
    String getImage();
    Collection<Answer> getAnswers();
}
