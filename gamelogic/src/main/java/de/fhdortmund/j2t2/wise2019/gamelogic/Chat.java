package de.fhdortmund.j2t2.wise2019.gamelogic;

import javafx.collections.transformation.SortedList;

import java.util.Collection;
import java.util.List;

public interface Chat {

    public long getId();

    Chatpartner getChatpartner();

    void addMessage(Message message);
    List<ChatMessage> getMessages();

    public class ChatMessage implements Message, Comparable<ChatMessage> {

        private final Message msg;
        private final long timestamp;

        public ChatMessage(Message msg, long timestamp) {
            this.msg = msg;
            this.timestamp = timestamp;
        }

        @Override
        public String getId() {
            return msg.getId();
        }

        @Override
        public String getText() {
            return msg.getText();
        }

        @Override
        public String getImage() {
            return msg.getImage();
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
        public Collection<? extends Answer> getAnswers() {
            return msg.getAnswers();
        }

        @Override
        public int compareTo(ChatMessage o) {
            return Long.compare(timestamp, o.timestamp);
        }
    }
}