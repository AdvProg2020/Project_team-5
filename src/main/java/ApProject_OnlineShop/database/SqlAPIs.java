package ApProject_OnlineShop.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
        return null;
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
        }
    }

    public List<T> getAllObjectsWithSort(String field, boolean asc) {
        return null;
    }

    public void save(T targetObject) {

    }

    public void delete(long id) {

    }

    public void delete(T targetObject) {

    }

    public boolean isExistObjectById(long id) {
        return true;
    }
}
