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
    private int delay;
    private boolean root;
    double probably;
    @OneToMany(mappedBy = "parent")
    List<AnswerEntity> answers;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private PointsEntity points;

    public PointsEntity getPoints() {
        return points;
    }

    public void setPoints(PointsEntity points) {
        this.points = points;
    }
}