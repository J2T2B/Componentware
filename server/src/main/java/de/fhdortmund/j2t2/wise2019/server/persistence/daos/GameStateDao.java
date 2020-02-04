package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.GameStateEntity;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Stateless
@Named
public class GameStateDao extends AbstractDao{

    @Inject
    ChatDao chatDao;

    public void persist(GameState<?> gameState, UserEntity owner) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        gameState.serializeData();
        GameStateEntity gameStateEntity = new GameStateEntity();
        gameStateEntity.setOwner(owner);
        gameStateEntity.setData(gameState.getSerializedData());
        em.persist(gameStateEntity);
        chatDao.persist(gameState.getOpenChats(), gameStateEntity);
        em.getTransaction().commit();
        em.close();
    }
}
