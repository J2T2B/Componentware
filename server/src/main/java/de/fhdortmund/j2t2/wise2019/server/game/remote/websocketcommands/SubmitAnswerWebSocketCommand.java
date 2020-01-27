package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class SubmitAnswerWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "SubmitAnswer";
    private final int answerId;
    private final int chatId;

    public SubmitAnswerWebSocketCommand(int answerId, int chatId) {
        super(COMMAND);
        this.answerId = answerId;
        this.chatId = chatId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public int getChatId() {
        return chatId;
    }
}

