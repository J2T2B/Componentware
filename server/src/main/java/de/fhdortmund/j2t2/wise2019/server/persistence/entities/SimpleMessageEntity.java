package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("S")
public class SimpleMessageEntity extends AbstractMessageEntity {
}
