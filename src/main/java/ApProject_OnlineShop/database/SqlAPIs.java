package ApProject_OnlineShop.database;

import javax.persistence.EntityManagerFactory;
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
        return null;
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
