package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ChatEntity {

    @Id
    private Long id;
    @OneToMany(mappedBy = "owner")
    private List<ChatMessageEntity> messages;
    @ManyToOne
    private GameStateEntity gameState;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private ChatPartnerEntity chatpartner;

    public ChatPartnerEntity getChatpartner() {
        return chatpartner;
    }

    public void setChatpartner(ChatPartnerEntity chatpartner) {
        this.chatpartner = chatpartner;
    }
}
