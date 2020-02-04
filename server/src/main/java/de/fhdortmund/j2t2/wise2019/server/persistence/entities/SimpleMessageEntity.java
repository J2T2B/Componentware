package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("S")
@Data
public class SimpleMessageEntity extends AbstractMessageEntity {
    private String text;
}
