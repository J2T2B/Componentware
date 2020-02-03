package de.fhdortmund.j2t2.wise2019.server.persistence;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.ChatMessage;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ChatEntity implements Chat {

    @Id
    private Long id;
    @OneToMany
    private List<ChatMessageEntity> messages;
    @ManyToOne
    private GameStateEntity gameState;
    private ChatPartnerEntity chatpartner;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public Chatpartner getChatpartner() {
        return chatpartner;
    }

    @Override
    public void addMessage(ChatMessage message) {
        /*ChatMessageEntity chatMessage = new ChatMessageEntity();
        chatMessage.setId(message.getId());
        chatMessage.setAnswer(message.isAnswer());
        chatMessage.setRead(message.isRead());
        //chatMessage.setMsg(message.getMsg());
        //messages.add(message);*/
    }

    @Override
    public List<ChatMessage> getMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public ChatMessage getMessage(String messageId) {
        return null;
    }
}
