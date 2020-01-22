package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.Collection;

public class MessageReference implements Message {

    private String id;
    private Message resolved;

    public MessageReference(String id) {
        this.id = id;
    }

    public void setResolved(Message resolved) {
        this.resolved = resolved;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return resolved.getText();
    }

    @Override
    public String getImage() {
        return resolved.getImage();
    }

    @Override
    public int getDelay() {
        return resolved.getDelay();
    }

    @Override
    public boolean isRoot() {
        return resolved.isRoot();
    }

    @Override
    public Points getPoints() {
        return resolved.getPoints();
    }

    @Override
    public double getProbably() {
        return resolved.getProbably();
    }

    @Override
    public Collection<? extends Answer> getAnswers() {
        return resolved.getAnswers();
    }
}
