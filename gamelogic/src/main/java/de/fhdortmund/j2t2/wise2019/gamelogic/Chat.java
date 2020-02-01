package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Chat {

    public long getId();

    Chatpartner getChatpartner();

    void addMessage(Message message, boolean isAnswer);
    List<ChatMessage> getMessages();

    ChatMessage getMessage(String messageId);

    public class ChatMessage implements Message, Comparable<ChatMessage> {

        private final String messageId = UUID.randomUUID().toString();
        private final Message msg;
        private final long timestamp;
        private boolean read = false;
        private boolean isAnswer;

        public ChatMessage(Message msg, long timestamp, boolean isAnswer) {
            this.msg = msg;
            this.timestamp = timestamp;
            this.isAnswer = isAnswer;
        }

        @Override
        public String getId() {
            return messageId;
        }

        @Override
        public String getText() {
            return msg.getText();
        }

        @Override
        public int getDelay() {
            return msg.getDelay();
        }

        @Override
        public boolean isRoot() {
            return msg.isRoot();
        }

        @Override
        public Points getPoints() {
            return msg.getPoints();
        }

        @Override
        public double getProbably() {
            return msg.getProbably();
        }

        @Override
        public List<? extends Answer> getAnswers() {
            return msg.getAnswers();
        }

        @Override
        public int compareTo(ChatMessage o) {
            return Long.compare(timestamp, o.timestamp);
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}