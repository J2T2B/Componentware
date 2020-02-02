package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.Collections;
import java.util.List;

public class DetectiveGameAnswer implements Answer {

    private transient DetectiveGameMessage parent;
    private transient int id;
    private String text;
    private String targetId;
    private String unlockKey;
    private boolean locked;
    private transient DetectiveGameMessage target;

    @Override
    public Message getParent() {
        return parent;
    }

    void setParent(DetectiveGameMessage parent) {
        this.parent = parent;
    }

    @Override
    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public List<? extends Message> getTargets() {
        return Collections.singletonList(target);
    }

    void setTarget(DetectiveGameMessage target) {
        this.target = target;
    }

    @Override
    public List<String> getTargetIds() {
        return Collections.singletonList(targetId);
    }
}
