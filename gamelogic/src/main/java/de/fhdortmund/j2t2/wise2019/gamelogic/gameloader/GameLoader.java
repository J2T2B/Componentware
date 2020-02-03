package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import com.google.gson.Gson;
import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class GameLoader {

    private Callable<InputStream> gameJsonStreamProvider;
    private Gson gson = new Gson();

    public GameLoader(Callable<InputStream> gameJsonStreamProvider) {
        if(gameJsonStreamProvider == null){
            throw new NullPointerException("gameJsonStream may not be null!");
        }
        this.gameJsonStreamProvider = gameJsonStreamProvider;
    }

    public synchronized <T extends Message> Map<String, T> loadGame(Class<T[]> msgClassArray, BiFunction<? extends Answer, Integer, BiConsumer<T, T[]>> resolveFunction) throws GameLoadingException {
        T[] loaded = loadGameData(msgClassArray);
        return resolveReferences(loaded, resolveFunction);
    }

    @SuppressWarnings("unchecked") //by design
    private <T extends Message, U extends Answer> Map<String, T> resolveReferences(T[] data, BiFunction<U, Integer, BiConsumer<T, T[]>> resolveFunction) {
        Map<String, T> res = new HashMap<>();
        for(T t : data) {
            res.put(t.getId(), t);
        }
        for(T t : data) {
            for(int id = 0; id < t.getAnswers().size(); id++) {
                Answer answer = t.getAnswers().get(id);
                T[] resolved = (T[]) Array.newInstance(t.getClass(), answer.getTargetIds().size());
                for(int i = 0; i < answer.getTargetIds().size(); i++) {
                    resolved[i] = res.get(answer.getTargetIds().get(i));
                }
                resolveFunction.apply((U) answer, id).accept(t, resolved);
            }
        }
        return res;
    }

    private <T extends Message> T[] loadGameData(Class<T[]> msgClassArray) throws GameLoadingException {
        try (Reader reader = new InputStreamReader(gameJsonStreamProvider.call())) {
            return gson.fromJson(reader, msgClassArray);
        } catch (Exception e) {
            throw new GameLoadingException("Unable to load game", e);
        }
    }
}
