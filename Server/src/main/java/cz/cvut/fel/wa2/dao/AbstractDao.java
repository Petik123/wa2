package cz.cvut.fel.wa2.dao;

import cz.cvut.fel.wa2.entities.PersistentEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public abstract class AbstractDao<E extends PersistentEntity> {
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    protected static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public void insert(E e) {
        final Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();

            session.saveOrUpdate(e);

            session.flush();

            tx.commit();
        } finally {
            session.close();
        }
    }

    public void update(E e) {
        final Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();

            session.saveOrUpdate(e);

            session.flush();

            tx.commit();
        } finally {
            session.close();
        }
    }

    public void delete(E e) {
        final Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();

            session.delete(e);

            session.flush();

            tx.commit();
        } finally {
            session.close();
        }
    }

    public List<E> getAll() {
        final Session session = getSession();
        List<E> list = null;
        try {
            Transaction tx = session.beginTransaction();

            list = session.createCriteria(getEntityClass()).list();

            session.flush();

            tx.commit();
        } finally {
            session.close();
            if (list == null) {
                list = new ArrayList<E>();
            }
        }
        return list;
    }

    public E getById(Long id) {
        final Session session = getSession();
        E e;
        try {
            Transaction tx = session.beginTransaction();


            e = (E) session.get(getEntityClass(), id);

            session.flush();

            tx.commit();
        } finally {
            session.close();
        }
        return e;
    }

    protected abstract Class<E> getEntityClass();

}

