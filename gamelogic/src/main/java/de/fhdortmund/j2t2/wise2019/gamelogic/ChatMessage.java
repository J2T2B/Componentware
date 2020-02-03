package de.fhdortmund.j2t2.wise2019.gamelogic;

public interface ChatMessage extends Comparable<ChatMessage>{
    String getId();
    String getText();
    SimpleMessage getMsg();
    boolean isRead();
    void setRead(boolean read);
    long getTimestamp();
    boolean isAnswer();
}
