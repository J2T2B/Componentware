package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.ChatPartner;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.ChatEntity;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.ChatPartnerEntity;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.GameStateEntity;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

@Named
@Stateful
public class ChatDao extends AbstractDao {

    @Inject
    private ChatMessageDao chatMessageDao;

    public void persist(List<Chat> chats, GameStateEntity gameState) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        for(Chat chat: chats){
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setId(chat.getId());
            chatEntity.setGameState(gameState);
            ChatPartnerEntity chatPartnerEntity = this.persist(chat.getChatpartner());
            chatEntity.setChatpartner(chatPartnerEntity);
            em.persist(chatEntity);
            chatMessageDao.persist(chat.getMessages(), chatEntity);
        }
        em.getTransaction().commit();
        em.close();
    }

    public ChatPartnerEntity persist(ChatPartner chatPartner){
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        ChatPartnerEntity chatPartnerEntity = new ChatPartnerEntity();
        chatPartnerEntity.setName(chatPartner.getName());
        chatPartnerEntity.setImageUri(chatPartner.getImageUri());
        em.persist(chatPartnerEntity);
        em.getTransaction().commit();
        em.close();
        return chatPartnerEntity;
    }
}