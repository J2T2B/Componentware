package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "answer")
public class AnswerEntity {
    @ManyToOne
    ComplexMessageEntity parent;
    @Id
    private int id;
    private String text;
    @OneToMany(mappedBy = "answer")
    List<TargetId> targetIds;
}
