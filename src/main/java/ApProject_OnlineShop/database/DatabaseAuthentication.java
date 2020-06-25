package ApProject_OnlineShop.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DatabaseAuthentication {
    public static void createConnection() {
        EntityManagerFactoryProducer.closeEntityManagerFactory();
    }

    private void addCustomer(int id, String username) {
        EntityManager entityManagerProducer = EntityManagerProducer.getInstanceOfEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManagerProducer.getTransaction();
            entityTransaction.begin();
            Student student = new Student(id, username);
            entityManagerProducer.persist(student);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null)
                entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManagerProducer.close();
        }
    }

    private void getCustomer(int id) {
        EntityManager entityManagerProducer = EntityManagerProducer.getInstanceOfEntityManager();
        EntityTransaction entityTransaction = null;
        String query = "SELECT c FROM testCustomer WHERE c.customerID = :custID";
        TypedQuery<Student> typedQuery = entityManagerProducer.createQuery(query, Student.class);
        typedQuery.setParameter("custID", id);
    }
}
