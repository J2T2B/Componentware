package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class SubmitAnswerWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "SubmitAnswer";
    private final int answerId;

    public SubmitAnswerWebSocketCommand(int answerId) {
        super(COMMAND);
        this.answerId = answerId;
    }

    public int getAnswerId() {
        return answerId;
    }
}

