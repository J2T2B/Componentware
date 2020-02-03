package de.fhdortmund.j2t2.wise2019.server.commons.remote;

public class ErrorWebSocketCommand extends AbstractWebSocketCommand {

    private final static String COMMAND = "HandleError";
    private String message;

    public ErrorWebSocketCommand(String message) {
        super(COMMAND);
        this.message = message;
    }
}
