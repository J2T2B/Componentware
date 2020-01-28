package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class AddMessageWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "AddMessage";
    private final long chatId;
    private final Message message;

    public AddMessageWebSocketCommand(long chatId, Message message) {
        super(COMMAND);
        this.chatId = chatId;
        this.message = message;
    }

    public long getChatId() { return chatId; }

    public Message getMessage() {
        return message;
    }
}

