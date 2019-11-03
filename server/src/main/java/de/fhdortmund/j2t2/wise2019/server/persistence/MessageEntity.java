package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

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
    public Collection<Answer> getAnswers() {
        return null;
    }
}
