package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class ReadMessageWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "ReadMessage";
    private final String messageId;
    private final long chatId;

    public ReadMessageWebSocketCommand(String messageId, long chatId) {
        super(COMMAND);
        this.messageId = messageId;
        this.chatId = chatId;
    }

    public String getMessageId() {
        return messageId;
    }

    public long getChatId() {
        return chatId;
    }
}

