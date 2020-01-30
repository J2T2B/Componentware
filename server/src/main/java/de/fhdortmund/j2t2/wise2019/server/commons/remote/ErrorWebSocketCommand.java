package de.fhdortmund.j2t2.wise2019.server.commons.remote;

public class ErrorWebSocketCommand extends AbstractWebSocketCommand {

    private final static String COMMAND = "HandleError";
    private Exception e;

    public ErrorWebSocketCommand(Exception e) {
        super(COMMAND);
        this.e = e;
    }
}
