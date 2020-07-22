package ApProject_OnlineShop.database.sqlMode.entityUtils;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerFactoryProducer;

import javax.persistence.EntityManager;

public class EntityManagerProducer {
    private static EntityManager entityManager;

    public static EntityManager getInstanceOfEntityManager() {
        if (entityManager == null || !entityManager.isOpen())
            entityManager = EntityManagerFactoryProducer.getInstanceOfEntityManagerFactory().createEntityManager();
        return entityManager;
    }

    public static void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen())
            entityManager.close();
        entityManager = null;
    }
}
