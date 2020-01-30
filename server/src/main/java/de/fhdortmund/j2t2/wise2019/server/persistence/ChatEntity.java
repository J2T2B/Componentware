package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.server.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat")
public class ChatEntity implements Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "chatpartner", nullable = false)
    private ChatpartnerEntity chatpartner;

    @ManyToMany(mappedBy = "chat")
    private List<ChatMessage> messages = new ArrayList<ChatMessage>(0);

    @ManyToOne
    private User owner;

    @Override
    public long getId() {  return id;  }

    @Override
    public Chatpartner getChatpartner() {
        return chatpartner;
    }

    public void setChatpartner(ChatpartnerEntity chatpartner) {
        this.chatpartner = chatpartner;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    @Override
    public ChatMessage getMessage(String messageId) {
        return null; //TODO
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public void addMessage(Message message, boolean isAnswer) {
        throw new UnsupportedOperationException("Not supported for chat entity");
    }
}
