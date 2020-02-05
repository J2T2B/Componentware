package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    String username;
    @Lob
    @Column(name = "data", columnDefinition = "BLOB")
    byte[] data;
}
