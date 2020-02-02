package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.List;

public class TestAnswer implements Answer {

    transient TestMessage parent;
    transient int id;
    String text;
    List<String> targetIds;
    transient List<TestMessage> targets;

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
    public List<? extends Message> getTargets() {
        return targets;
    }

    @Override
    public List<String> getTargetIds() {
        return targetIds;
    }
}
