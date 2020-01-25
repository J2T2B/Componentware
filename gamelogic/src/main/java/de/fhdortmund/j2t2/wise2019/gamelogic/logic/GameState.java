package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

import java.util.List;

/**
 *
 * @param <T> Some Kind of Data holder
 */
public class GameState<T> {
    private List<Chat> openChats;
    private T data;

    public List<Chat> getOpenChats() {
        return openChats;
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
}
