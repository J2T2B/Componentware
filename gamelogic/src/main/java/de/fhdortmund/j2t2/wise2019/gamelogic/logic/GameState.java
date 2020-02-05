package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @param <T> Some Kind of Data holder
 */
public class GameState<T extends Serializable> implements Serializable {
    private List<Chat> openChats = new ArrayList<>();
    private T data;
    private transient Gson gson = new Gson();


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
}
