package de.fhdortmund.j2t2.wise2019.server.persistence.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long numeric_id;
    private String id;
    private String text;

    private boolean alreadySeen;
    private long creationTime;
    private boolean answer;

    @ManyToOne
    private ChatEntity owner;
    @OneToOne(cascade = CascadeType.ALL)
    private AbstractMessageEntity message;
}
