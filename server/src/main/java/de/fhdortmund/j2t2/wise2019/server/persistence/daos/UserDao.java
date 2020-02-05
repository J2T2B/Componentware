package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.UserEntity;
import de.fhdortmund.j2t2.wise2019.server.user.DefaultUserImpl;
import de.fhdortmund.j2t2.wise2019.server.user.User;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.*;

@Named
@Stateful
public class UserDao{
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("de.fhdortmund.j2t2.wise2019.server.persistence");

    public void persist(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos)
        ) {
            delete(user);
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(user.getName());
            oos.writeObject(user);
            userEntity.setData(bos.toByteArray());
            em.persist(userEntity);
            em.getTransaction().commit();
        } catch (IOException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(User user){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaDelete<UserEntity> criteria = builder.createCriteriaDelete(UserEntity.class);
        Root<UserEntity> from = criteria.from(UserEntity.class);
        criteria.where(builder.equal(from.get("username"), user.getName()));
        Query query = em.createQuery(criteria);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public User get(String username) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        UserEntity entity;

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = builder.createQuery(UserEntity.class);
        Root<UserEntity> from = criteria.from(UserEntity.class);
        criteria.select(from).where(builder.equal(from.get("username"), username));
        TypedQuery<UserEntity> query = em.createQuery(criteria);

        try {
            entity = query.getSingleResult();
        } catch (NoResultException e){
            throw e;
        } finally {
            em.getTransaction().commit();
        }
        User user;
        try(ByteArrayInputStream bis = new ByteArrayInputStream(entity.getData());
            ObjectInputStream ois = new ObjectInputStream(bis)
        ) {
            user = (User) ois.readObject();
        } finally {
            em.close();
        }
        return user;
    }
}
