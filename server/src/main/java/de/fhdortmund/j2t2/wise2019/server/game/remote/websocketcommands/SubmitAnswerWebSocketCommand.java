package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class SubmitAnswerWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "SubmitAnswer";
    private final long answerId;
    private final long chatId;
    private final String messageId;

    public SubmitAnswerWebSocketCommand(int answerId, int chatId, String messageId) {
        super(COMMAND);
        this.answerId = answerId;
        this.chatId = chatId;
        this.messageId = messageId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public long getChatId() {
        return chatId;
    }

    public String getMessageId() {
        return messageId;
    }
}

