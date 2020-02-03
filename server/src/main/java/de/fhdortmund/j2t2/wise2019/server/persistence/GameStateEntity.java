package de.fhdortmund.j2t2.wise2019.server.persistence;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class GameStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(mappedBy = "gamestateentity")
    private List<ChatEntity> chats;
    @ManyToOne
    private UserEntity owner;
}
