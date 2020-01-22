package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.Collection;

public class ResolvedMessage implements Message {

    private String id;
    private String text;
    private String image;
    private int delay;
    private boolean root;
    private Points points;
    private double probability;
    private Collection<? extends Answer> answers;

    public ResolvedMessage(String id, String text, String image, int delay, boolean root, Points points, double probability, Collection<? extends Answer> answers) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.delay = delay;
        this.root = root;
        this.points = points;
        this.probability = probability;
        this.answers = answers;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getImage() {
        return this.image;
    }

    @Override
    public int getDelay() {
        return this.delay;
    }

    @Override
    public boolean isRoot() {
        return this.root;
    }

    @Override
    public Points getPoints() {
        return this.points;
    }

    @Override
    public double getProbably() {
        return this.probability;
    }

    @Override
    public Collection<? extends Answer> getAnswers() {
        return this.answers;
    }
}
