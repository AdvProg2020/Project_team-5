package ApProject_OnlineShop.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProducer {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getInstanceOfEntityManagerFactory() {
        if (entityManagerFactory == null)
            Persistence.createEntityManagerFactory("Project_team-5");
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen())
            entityManagerFactory.close();
    }
}
