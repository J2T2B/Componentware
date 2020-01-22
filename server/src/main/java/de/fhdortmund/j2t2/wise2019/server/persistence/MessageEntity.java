package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.Collection;

public class MessageEntity implements Message {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getText() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public Points getPoints() {
        return null;
    }

    @Override
    public double getProbably() {
        return 0;
    }

    @Override
    public Collection<Answer> getAnswers() {
        return null;
    }
}
