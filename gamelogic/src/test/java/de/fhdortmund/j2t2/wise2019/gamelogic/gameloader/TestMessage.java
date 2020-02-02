package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.Collection;
import java.util.List;

public class TestMessage implements Message {

    private String id;
    private int delay;
    private String text;
    private List<TestAnswer> answers;
    private boolean root = false;
    private double probably = 1;
    private TestPoints points;

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
        return probably;
    }

    @Override
    public List<? extends Answer> getAnswers() {
        return answers;
    }
}
