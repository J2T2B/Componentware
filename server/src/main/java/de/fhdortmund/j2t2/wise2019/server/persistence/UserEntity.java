package de.fhdortmund.j2t2.wise2019.server.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    private String username;
    private String hash;
    @OneToMany(mappedBy = "user")
    private List<GameStateEntity> games;
}
