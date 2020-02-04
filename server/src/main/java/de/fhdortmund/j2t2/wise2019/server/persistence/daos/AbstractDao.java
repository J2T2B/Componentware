package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AbstractDao {
    EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("de.fhdortmund.j2t2.wise2019.server.persistence");
}
