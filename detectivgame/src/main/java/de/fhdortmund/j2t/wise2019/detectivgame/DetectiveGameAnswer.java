package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;

import java.util.Collections;
import java.util.List;

public class DetectiveGameAnswer implements Answer {

    private String text;
    private String targetId;
    private String unlockKey;
    private boolean locked;
    private LockedIfCondition lockedIf;
    private String removeAnswers;

    private DetectiveGameMessage parent;
    private int id;
    private DetectiveGameMessage target;

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

    public String getUnlockKey() {
        return unlockKey;
    }

    public boolean isLocked(GameModel model) {
        return locked || (lockedIf != null && lockedIf.isLocked(model,this));
    }

    public String getRemoveAnswers() {
        return removeAnswers;
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

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
