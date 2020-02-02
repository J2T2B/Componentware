package de.fhdortmund.j2t2.wise2019.gamelogic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;

import java.io.Serializable;

public class ChatpartnerImpl implements Chatpartner, Serializable {

    private String name;
    private String imageUri;

    public ChatpartnerImpl(){
        this.name = "Bill";
        this.imageUri = "https://s3.amazonaws.com/baconmockup/img/bm-home-140.jpg";
    }

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
