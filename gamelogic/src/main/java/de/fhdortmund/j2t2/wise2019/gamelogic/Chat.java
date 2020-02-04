package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public interface Chat {

    public long getId();

    ChatPartner getChatpartner();

    void addMessage(ChatMessage message);
    List<ChatMessage> getMessages();

    ChatMessage getMessage(String messageId);

    public class ChatMessageImpl implements ChatMessage {

        private final String messageId = UUID.randomUUID().toString();
        private final SimpleMessage msg;
        private final long timestamp;
        private boolean read = false;
        private boolean isAnswer;

        public ChatMessageImpl(SimpleMessage msg, long timestamp, boolean isAnswer) {
            this.msg = msg;
            this.timestamp = timestamp;
            this.isAnswer = isAnswer;
        }

        public ChatMessageImpl(Message msg) {
            this.msg = msg;
            this.timestamp = Calendar.getInstance().getTimeInMillis();
            isAnswer = false;
        }

        public ChatMessageImpl(String msg, long timestamp, boolean isAnswer){
            this(() -> {return msg;}, timestamp, isAnswer);
        }

        public String getId() {
            return messageId;
        }

        public String getText() {
            return msg.getText();
        }

        public SimpleMessage getMsg() {
            return msg;
        }

        @Override
        public int compareTo(ChatMessage o) {
            return Long.compare(timestamp, o.getTimestamp());
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

        public boolean isAnswer() {
            return isAnswer;
        }
    }
}