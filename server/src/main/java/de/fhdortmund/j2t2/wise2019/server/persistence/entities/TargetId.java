package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TargetId {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String targetId;
    @ManyToOne
    private AnswerEntity answer;
}
