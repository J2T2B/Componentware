package de.fhdortmund.j2t2.wise2019.server.game.remote.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;

public class AnswerRemoteModel {

    private long id;
    private String text;

    public AnswerRemoteModel(Answer answer){
        this.id = answer.getId();
        this.text = answer.getText();
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
