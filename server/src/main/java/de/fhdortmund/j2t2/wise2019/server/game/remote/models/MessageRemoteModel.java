package de.fhdortmund.j2t2.wise2019.server.game.remote.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Nur für's senden und empfangen gedacht
 */
public class MessageRemoteModel {

    private String id;
    private String text;
    private boolean isAnswer = false;
    private List<AnswerRemoteModel> answers;
    private boolean userHasRead;
    private long created;

    public MessageRemoteModel(Chat.ChatMessage message) {
        this.id = message.getId();
        this.text = message.getText();
        this.userHasRead = message.isRead();
        this.created = message.getTimestamp();
        this.isAnswer = message.isAnswer();


        if(message.getMsg() instanceof Message){
            this.answers = ((Message) message.getMsg()).getAnswers().stream().map(AnswerRemoteModel::new).collect(Collectors.toList());
        }
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public List<AnswerRemoteModel> getAnswers() {
        return answers;
    }

    public boolean isUserHasRead() {
        return userHasRead;
    }

    public long getCreated() {
        return created;
    }
}
