package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.ArrayList;
import java.util.List;

public class MessageEntity implements Message {

    private String id;
    private String text;
    private int delay;
    private boolean root;
    private Points points;
    private double probability;
    private List<Answer> answers = new ArrayList<>(0);
    private boolean read = false;

    //TODO
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    @Override
    public Points getPoints() {
        return points;
    }

    @Override
    public double getProbably() {
        return probability;
    }

    @Override
    public List<Answer> getAnswers() {
        return answers;
    }
}
