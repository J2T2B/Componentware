package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.ChatMessage;
import de.fhdortmund.j2t2.wise2019.gamelogic.SimpleMessage;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChatMessageEntity implements ChatMessage {
    @Id
    private String id;
    private String text;

    private AbstractMessageEntity msg;
    private boolean read;
    private long timestamp;
    private boolean answer;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public SimpleMessage getMsg() {
        return (SimpleMessage) msg;
    }

    public void setMsg(AbstractMessageEntity msg) {
        this.msg = msg;
    }

    @Override
    public boolean isRead() {
        return read;
    }

    @Override
    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public int compareTo(@NotNull ChatMessage o) {
        return Long.compare(timestamp, o.getTimestamp());
    }
}
