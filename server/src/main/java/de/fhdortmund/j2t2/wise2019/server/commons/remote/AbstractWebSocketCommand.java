package de.fhdortmund.j2t2.wise2019.server.commons.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

public abstract class AbstractWebSocketCommand {
    private String command;

    public AbstractWebSocketCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
