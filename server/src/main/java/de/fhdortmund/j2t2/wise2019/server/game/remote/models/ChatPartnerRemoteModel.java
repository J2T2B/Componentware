package de.fhdortmund.j2t2.wise2019.server.game.remote.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.ChatPartner;

public class ChatPartnerRemoteModel {

    private String name;
    private String imageUrl;

    public ChatPartnerRemoteModel(ChatPartner chatpartner) {
        this.name = chatpartner.getName();
        this.imageUrl = chatpartner.getImageUri();
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
