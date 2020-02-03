package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.models.MessageRemoteModel;

public class AddMessageWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "AddMessage";
    private final String chatId;
    private final MessageRemoteModel message;

    public AddMessageWebSocketCommand(long chatId, Chat.ChatMessageImpl message) {
        super(COMMAND);
        this.chatId = chatId+"";
        this.message = new MessageRemoteModel(message);
    }

    public long getChatId() { return Long.parseLong(chatId); }

    public MessageRemoteModel getMessage() {
        return message;
    }
}

