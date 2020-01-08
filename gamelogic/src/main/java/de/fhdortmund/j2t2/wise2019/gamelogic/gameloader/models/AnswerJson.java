package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.AnswerDetailed;

public class AnswerJson implements AnswerDetailed {

    private int id;
    private String text;
    private String[] targets;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    public String[] getTargets() {
        return targets;
    }
}
