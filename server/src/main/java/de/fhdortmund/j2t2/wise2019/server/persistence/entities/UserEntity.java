package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String hash;
    @OneToMany(mappedBy = "owner")
    private List<GameStateEntity> games;
}
