package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.GameStateEntity;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.UserEntity;
import de.fhdortmund.j2t2.wise2019.server.user.DefaultUserImpl;
import de.fhdortmund.j2t2.wise2019.server.user.User;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;
import org.hibernate.Criteria;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Named
@Stateless
public class UserDao extends AbstractDao{
    @Inject
    private GameStateDao gameStateDao;

    public void persist(User user) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getName());
        userEntity.setHash(user.getHash());
        userEntity.setGames(new ArrayList<>());
        em.persist(userEntity);
        user.getGames().stream().map(Game::getGameState).forEach(gs -> gameStateDao.persist(gs, userEntity));
        em.getTransaction().commit();
        em.close();
    }

    public User get(String username) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        UserEntity entity;

        EntityManager em = sessionFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = builder.createQuery(UserEntity.class);
        Root<UserEntity> from = criteria.from(UserEntity.class);
        criteria.select(from).where(builder.equal(from.get("username"), username));
        TypedQuery<UserEntity> query = em.createQuery(criteria);

        try {
            entity = query.getSingleResult();
        } catch (NoResultException e){
            throw e;
        }

        List<GameState> gameStates = entity.getGames().stream().map(gameStateDao::deserialize).collect(Collectors.toList());
        List<Game> games = new ArrayList<>();
        for(GameState gameState : gameStates){
            Class<? extends Game> gameClass = gameState.getGameClazz();
            Game game = gameClass.newInstance();
            game.setGameState(gameState);
        }

        User user = new DefaultUserImpl(entity.getUsername(), entity.getHash(), games);
        return user;
    }
}
