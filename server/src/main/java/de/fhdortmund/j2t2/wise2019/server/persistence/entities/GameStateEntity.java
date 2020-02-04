package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class GameStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToMany(mappedBy = "gameState")
    private List<ChatEntity> chats;
    @ManyToOne
    private UserEntity owner;
    private String data;
}
