package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class ReadMessageWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "ReadMessage";
    private final String messageId;

    public ReadMessageWebSocketCommand(String messageId) {
        super(COMMAND);
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}

