package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.AnswerDetailed;
import de.fhdortmund.j2t2.wise2019.gamelogic.MessageDetailed;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MessageJson implements MessageDetailed, Serializable {

    private String id;
    private int delay;
    private String text;
    private AnswerJson[] answers;
    private boolean isRoot = false;
    private PointsJson points;
    private double probably = 1;
    private String image;

    private transient List<AnswerJson> cachedAnswers;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public Collection<? extends AnswerDetailed> getAnswers() {
        if(cachedAnswers == null){
            cachedAnswers = Arrays.asList(answers);
        }

        return cachedAnswers;
    }

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
