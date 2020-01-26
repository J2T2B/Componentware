package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class AddAnswerWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "AddAnswer";
    private final int chatId;
    private final String messageId;
    private final Answer answer;

    public AddAnswerWebSocketCommand(int chatId, String messageId, Answer answer) {
        super(COMMAND);
        this.chatId = chatId;
        this.messageId = messageId;
        this.answer = answer;
    }

    public int getChatId() {
        return chatId;
    }

    public String getMessageId() {
        return messageId;
    }

    public Answer getAnswer() {
        return answer;
    }
}

