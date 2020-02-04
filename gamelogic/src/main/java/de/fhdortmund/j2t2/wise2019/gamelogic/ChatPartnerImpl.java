package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.io.Serializable;

public class ChatPartnerImpl implements ChatPartner, Serializable {

    private String name;
    private String imageUri;

    public ChatPartnerImpl(){
        this.name = "Bill";
        this.imageUri = "https://s3.amazonaws.com/baconmockup/img/bm-home-140.jpg";
    }

    public ChatPartnerImpl(ChatPartner chatpartner){
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
