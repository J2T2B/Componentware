package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.Collection;
import java.util.List;

public class DetectiveGameMessage implements Message {

    private String id;
    private int delay;
    private String text;
    private List<DetectiveGameAnswer> answers;
    private boolean root = false;
    private List<String> unlockKeys;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<? extends Answer> getAnswers() {  return answers;  }

    public int getDelay() {
        return delay;
    }

    public boolean isRoot() {
        return root;
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