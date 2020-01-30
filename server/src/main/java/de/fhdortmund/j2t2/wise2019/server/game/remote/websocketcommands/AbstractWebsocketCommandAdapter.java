package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import com.google.gson.*;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

import java.lang.reflect.Type;

public class AbstractWebsocketCommandAdapter implements JsonDeserializer<AbstractWebSocketCommand>{
    private static final String COMMANDTYPE_FIELD_NAME = "command";

    @Override
    public AbstractWebSocketCommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive primCommandName = jsonObject.getAsJsonPrimitive(COMMANDTYPE_FIELD_NAME);
        String commandName = primCommandName.getAsString();

        //WebSocketCommand Klassen immer in der Form command + "WebSocketCommand", deswegen hier die Zuordnung möglich
        String className = String.format("de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.%sWebSocketCommand", commandName);

        Class<?> clazz;
        try{
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(className + " not found", e);
        }
        return context.deserialize(json, clazz);
    }
}
