package de.fhdortmund.j2t2.wise2019.server.game.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.AbstractWebsocketCommandAdapter;

import javax.websocket.*;

public class MessageCoder implements Decoder.Text<AbstractWebSocketCommand>, Encoder.Text<AbstractWebSocketCommand> {

    private Gson gson;

    @Override
    public AbstractWebSocketCommand decode(String s) throws DecodeException {
        return gson.fromJson(s, AbstractWebSocketCommand.class);
    }

    @Override
    public boolean willDecode(String s) {
        try {
            gson.fromJson(s, AbstractWebSocketCommand.class);
            return true;
        } catch (Throwable t){
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AbstractWebSocketCommand.class, new AbstractWebsocketCommandAdapter());
        gson = gsonBuilder.create();
    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(AbstractWebSocketCommand object) throws EncodeException {
        return gson.toJson(object);
    }
}
