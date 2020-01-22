package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.io.Serializable;
import java.util.List;

public class MessageJson implements Serializable {

    private String id;
    private int delay;
    private String text;
    private AnswerJson[] answers;
    private boolean isRoot = false;
    private PointsJson points;
    private double probably = 1;
    private String image;

    private transient List<AnswerJson> cachedAnswers;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public AnswerJson[] getAnswers() {  return answers;  }

    public int getDelay() {
        return delay;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public Points getPoints() {
        return points;
    }

    public double getProbably() {
        return probably;
    }
}
