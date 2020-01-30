package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

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
