package de.fhdortmund.j2t2.wise2019.server.commons.remote;

public class ErrorWebSocketCommand extends AbstractWebSocketCommand {

    private final static String COMMAND = "ErrorMessage";

    public ErrorWebSocketCommand() {
        super(COMMAND);
    }
}
