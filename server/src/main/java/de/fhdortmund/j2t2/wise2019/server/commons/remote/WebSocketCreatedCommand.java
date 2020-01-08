package de.fhdortmund.j2t2.wise2019.server.commons.remote;

public class WebSocketCreatedCommand extends AbstractWebSocketCommand{

    private static final String COMMAND = "WebSocketCreated";

    public WebSocketCreatedCommand(){
        super(COMMAND);
    }
}
