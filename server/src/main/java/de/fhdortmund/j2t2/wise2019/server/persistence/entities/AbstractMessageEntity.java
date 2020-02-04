package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import de.fhdortmund.j2t2.wise2019.gamelogic.SimpleMessage;
import lombok.Data;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "M_TYPE", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class AbstractMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long db_id;
    private String text;
}
