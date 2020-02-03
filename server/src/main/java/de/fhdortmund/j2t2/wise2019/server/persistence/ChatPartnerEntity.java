package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class ChatPartnerEntity implements Chatpartner {
    private String name;
    private String imageUri;
}
