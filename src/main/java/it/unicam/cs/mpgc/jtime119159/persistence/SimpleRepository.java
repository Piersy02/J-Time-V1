package it.unicam.cs.mpgc.jtime119159.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SimpleRepository<T> implements Repository<T> {

    private final Class<T> entityClass;

    public SimpleRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        }
    }

    @Override
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        }
    }

    @Override
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        }
    }

    @Override
    public T findById(Long id) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.get(entityClass, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            Query<T> query = session.createQuery("from " + entityClass.getName(), entityClass);
            return query.list();
        }
    }
}
