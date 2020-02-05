package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.List;

public class DetectiveGameMessage implements Message {

    private String id;
    private int delay;
    private String text;
    private List<DetectiveGameAnswer> answers;
    private boolean root;
    private String chatName;
    private String chatImage;
    private List<String> unlockKeys;
    private boolean end;

    public DetectiveGameMessage(String id, int delay, String text, List<DetectiveGameAnswer> answers, boolean root) {
        this.id = id;
        this.delay = delay;
        this.text = text;
        this.answers = answers;
        this.root = root;
    }

    public DetectiveGameMessage(String id, int delay, String text, List<DetectiveGameAnswer> answers, boolean root, String chatName, String chatImage, List<String> unlockKeys, boolean end) {
        this.id = id;
        this.delay = delay;
        this.text = text;
        this.answers = answers;
        this.root = root;
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.unlockKeys = unlockKeys;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<DetectiveGameAnswer> getAnswers() {  return answers;  }

    public int getDelay() {
        return delay;
    }

    public boolean isRoot() {
        return root;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatImage() {
        return chatImage;
    }

    public List<String> getUnlockKeys() {
        return unlockKeys;
    }

    public boolean isEnd() {
        return end;
    }

    private Points zeroPoints = new Points() {
        @Override
        public int getChefSatisfaction() {
            return 0;
        }

        @Override
        public int getCustomerExperience() {
            return 0;
        }

        @Override
        public int getBudget() {
            return 0;
        }

        @Override
        public Points add(Points p) {
            return this;
        }

        @Override
        public boolean anyZeroOrLower() {
            return false;
        }
    };

    @Override
    public Points getPoints() {
        return zeroPoints;
    }

    @Override
    public double getProbably() {
        return 0;
    }
}