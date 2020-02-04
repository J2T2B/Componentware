package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.ChatMessage;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.AbstractMessageEntity;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.ChatEntity;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.ChatMessageEntity;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

@Named
@Stateful
public class ChatMessageDao extends AbstractDao{

    @Inject
    private MessageDao messageDao;

    public void persist(List<ChatMessage> messages, ChatEntity chatEntity) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        for(ChatMessage message :messages){
            ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
            chatMessageEntity.setAnswer(message.isAnswer());
            chatMessageEntity.setId(message.getId());
            chatMessageEntity.setText(message.getText());
            chatMessageEntity.setOwner(chatEntity);
            chatMessageEntity.setCreationTime(message.getTimestamp());
            chatMessageEntity.setAlreadySeen(message.isRead());
            AbstractMessageEntity messageEntity = messageDao.persist(message.getMsg());
            chatMessageEntity.setMessage(messageEntity);
            em.persist(chatEntity);
        }
        em.getTransaction().commit();
        em.close();
    }
}
