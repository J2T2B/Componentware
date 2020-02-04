package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("C")
@Data
public class ComplexMessageEntity extends AbstractMessageEntity {
    private String id;
    private String gameClazz;
}
