package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.models.MessageRemoteModel;

public class AddMessageWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "AddMessage";
    private final long chatId;
    private final MessageRemoteModel message;

    public AddMessageWebSocketCommand(long chatId, Chat.ChatMessage message) {
        super(COMMAND);
        this.chatId = chatId;
        this.message = new MessageRemoteModel(message);
    }

    public long getChatId() { return chatId; }

    public MessageRemoteModel getMessage() {
        return message;
    }
}

