package de.fhdortmund.j2t.wise2019.detectivgame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectiveGameData implements Serializable {

    private int sendTelefonDatenCount = 0;
    private boolean telefonDatenSent = false;
    private List<String> removedAnswerIds = new ArrayList<>();
    private Map<String, Integer> singleRemovedAnswers = new HashMap<>();
    private List<String> unlockKeys = new ArrayList<>();

    public int getSendTelefonDatenCount() {
        return sendTelefonDatenCount;
    }

    public void setSendTelefonDatenCount(int sendTelefonDatenCount) {
        this.sendTelefonDatenCount = sendTelefonDatenCount;
    }

    public boolean isTelefonDatenSent() {
        return telefonDatenSent;
    }

    public void setTelefonDatenSent(boolean telefonDatenSent) {
        this.telefonDatenSent = telefonDatenSent;
    }

    public List<String> getRemovedAnswerIds() {
        return removedAnswerIds;
    }

    public Map<String, Integer> getSingleRemovedAnswers() {
        return singleRemovedAnswers;
    }

    public List<String> getUnlockKeys() {
        return unlockKeys;
    }

    public void addRemovedAnswerId(String removeAnswers) {
        removedAnswerIds.add(removeAnswers);
    }

    public void addSingleRemovedAnswer(String msgId, int answerId) {
        singleRemovedAnswers.put(msgId, answerId);
    }

    public void addUnlockKey(String unlockKey) {
        unlockKeys.add(unlockKey);
    }
}