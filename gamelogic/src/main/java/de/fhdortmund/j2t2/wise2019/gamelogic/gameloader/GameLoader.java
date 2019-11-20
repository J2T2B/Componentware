package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import com.google.gson.Gson;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models.MessageJson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GameLoader {

    private String gameJsonPath;
    private Gson gson = new Gson();

    private List<MessageJson> cachedMessages;

    public GameLoader(String gameJsonPath) {
        if(gameJsonPath == null){
            throw new NullPointerException("gameJsonPath may not be null!");
        }
        this.gameJsonPath = gameJsonPath;
    }

    public List<? extends Message> loadGame() throws GameLoadingException {
        if (cachedMessages == null) {
            try (FileReader reader = new FileReader(gameJsonPath)) {
                MessageJson[] messages = gson.fromJson(reader, MessageJson[].class);
                cachedMessages = Arrays.asList(messages);
            } catch (IOException e) {
                throw new GameLoadingException("Unable to load game", e);
            }
        }

        return cachedMessages;
    }
}
