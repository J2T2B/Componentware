package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.GameStateEntity;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public GameState deserialize(GameStateEntity gameStateEntity) {
        GameState gameState;
        try {
            gameState = new GameState(Class.forName(gameStateEntity.getClazz()), Class.forName(gameStateEntity.getGameClazz()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        gameState.setSerializedData(gameStateEntity.getData());
        gameState.deserializeData();
        List<Chat> chats = gameStateEntity.getChats().stream().map(ce -> chatDao.deserialize(ce, gameState)).collect(Collectors.toList());
        gameState.setOpenChats(chats);
        return gameState;
    }
}
