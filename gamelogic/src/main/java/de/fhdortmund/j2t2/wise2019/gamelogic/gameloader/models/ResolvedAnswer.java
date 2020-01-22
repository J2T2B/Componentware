package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.Collection;

public class ResolvedAnswer implements Answer {

    private Message parent;
    private int id;
    private Collection<Message> targets;
    private String text;

    public ResolvedAnswer(Message parent, int id, Collection<Message> targets, String text) {
        this.parent = parent;
        this.id = id;
        this.targets = targets;
        this.text = text;
    }

    @Override
    public Message getParent() {
        return parent;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Collection<Message> getTargets() {
        return targets;
    }
}
