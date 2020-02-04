package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.UserEntity;
import de.fhdortmund.j2t2.wise2019.server.user.User;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.ArrayList;

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

    public User get(String username) {
        return null;
    }
}
