package br.com.andydkeh.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BaseDAO<T> {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("app");
    protected static EntityManager em = factory.createEntityManager();
    private final Class<T> entityClass;

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public T findById(Long id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        return query.getResultList();
    }

    public void update(T entity) {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void delete(Long id) {
        try {
            T entity = findById(id);
            if (entity != null) {
                em.getTransaction().begin();
                em.remove(entity);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void close() {
        em.close();
        factory.close();
    }
}
