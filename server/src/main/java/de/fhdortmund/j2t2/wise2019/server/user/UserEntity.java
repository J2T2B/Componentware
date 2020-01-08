package de.fhdortmund.j2t2.wise2019.server.user;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@ApplicationScoped
public class UserEntity {
    @Id
    private String username;
    private String hash;
}
