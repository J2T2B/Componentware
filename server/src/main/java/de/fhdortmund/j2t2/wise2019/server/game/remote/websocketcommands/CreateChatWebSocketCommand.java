package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatRemoteModel;

public class CreateChatWebSocketCommand extends AbstractWebSocketCommand {

private static final String COMMAND = "CreateChat";
private final Chat chat;

public CreateChatWebSocketCommand(ChatRemoteModel chat) {
        super(COMMAND);
        this.chat = chat;
        }

public Chat getChat() {
        return chat;
        }
        }
