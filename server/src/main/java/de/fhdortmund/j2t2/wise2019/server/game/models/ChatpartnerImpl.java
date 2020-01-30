package de.fhdortmund.j2t2.wise2019.server.game.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;

import java.io.Serializable;

public class ChatpartnerImpl implements Chatpartner, Serializable {

    private String name;
    private String imageUri;
    public ChatpartnerImpl(Chatpartner chatpartner){
        this.name = chatpartner.getName();
        this.imageUri = chatpartner.getImageUri();
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageUri() {
        return imageUri;
    }
}
