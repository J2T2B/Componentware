package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @param <T> Some Kind of Data holder
 */
public class GameState<T> {
    private List<Chat> openChats = new ArrayList<>();
    private T data;
    private String serializedData;
    private transient Gson gson = new Gson();
    private final Class<? extends T> clazz;
    private final Class<? extends Game> gameClazz;

    public GameState(Class<? extends T> clazz, Class<? extends Game> gameClazz){
        this.clazz = clazz;
        this.gameClazz = gameClazz;
    }

    public List<Chat> getOpenChats() {
        return openChats;
    }

    public Chat getChat(long chatId){
        return openChats.stream().filter(c -> c.getId() == chatId).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public void setOpenChats(List<Chat> openChats) {
        this.openChats = openChats;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void addChat(Chat chat) {
        openChats.add(chat);
    }

    public void serializeData(){
        serializedData = gson.toJson(data);
    }

    public void deserializeData(){
        data = gson.fromJson(serializedData, clazz);
    }

    public String getSerializedData(){
        if(serializedData == null || serializedData.isEmpty()){
            serializeData();
        }
        return serializedData;
    }

    public void setSerializedData(String serializedData){
        this.serializedData = serializedData;
    }

    public Class<? extends T> getClazz() {
        return clazz;
    }

    public Class<? extends Game> getGameClazz() {
        return gameClazz;
    }
}
