package ApProject_OnlineShop.database;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class SqlAPIs<T> {
    protected EntityManagerFactory entityManagerFactory;
    protected Class<T> classOfT;

    public SqlAPIs(Class<T> classOfT) {
        entityManagerFactory = EntityManagerFactoryProducer.getInstanceOfEntityManagerFactory();
        this.classOfT = classOfT;
    }

    public T getObjectById(long id) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        try {
            return entityManager.find(classOfT, id);
        } catch (Exception e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<T> getAllObjects() {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(classOfT);
        Root<T> root = query.from(classOfT);

        query.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } finally {
            entityManager.close();
        }
    }

    public List<T> getAllObjectsWithSort(String field, boolean asc) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(classOfT);
        Root<T> root = query.from(classOfT);

        query.select(root);
        if (field != null && !field.isEmpty()) {
            try {
                if (asc)
                    query.orderBy(criteriaBuilder.asc(root.get(field)));
                else
                    query.orderBy(criteriaBuilder.desc(root.get(field)));
            } catch (Exception e) {
                e.printStackTrace();
                query.getOrderList().removeIf(order -> true);
            }
        }
        try {
            TypedQuery<T> typedQuery = entityManager.createQuery(query);
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } finally {
            entityManager.close();
        }
    }

    public void save(T targetObject) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            long id = -1L;
            if (targetObject != null)
                id = (long) entityManagerFactory.getPersistenceUnitUtil().getIdentifier(targetObject);

            if (id == 0)
                entityManager.persist(targetObject);
            else if (id != -1)
                entityManager.merge(targetObject);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null)
                entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void delete(long id) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        try {
            T targetObject = entityManager.find(classOfT, id);
            delete(targetObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void delete(T targetObject) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(targetObject);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null)
                entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public boolean isExistObjectById(long id) {
        return getObjectById(id) != null;
    }
}
