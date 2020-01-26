package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class GameOverWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "GameOver";

    public GameOverWebSocketCommand() {
        super(COMMAND);
    }
}
