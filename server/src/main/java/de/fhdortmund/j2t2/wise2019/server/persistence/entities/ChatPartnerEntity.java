package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import de.fhdortmund.j2t2.wise2019.gamelogic.ChatPartner;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ChatPartnerEntity implements ChatPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String imageUri;
}
