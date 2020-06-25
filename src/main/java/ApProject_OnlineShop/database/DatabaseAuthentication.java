package ApProject_OnlineShop.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DatabaseAuthentication {
    public static void createConnection() {
        addCustomer(7, "cliff");
        getCustomer(1);
        getAllCustomers();
        EntityManagerFactoryProducer.closeEntityManagerFactory();
    }

    private static void addCustomer(int id, String username) {
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
        } /*finally {
            entityManagerProducer.close();
        }*/
    }

    private static void getCustomer(int id) {
        EntityManager entityManagerProducer = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder cb = entityManagerProducer.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);

        cq.select(root).where(cb.equal(root.get("id"), id));
        TypedQuery<Student> typedQuery = entityManagerProducer.createQuery(cq);
        Student student;
        try {
            student = typedQuery.getSingleResult();
            System.out.println(student.getId() + " " + student.getUsername());
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        } /*finally {
            entityManagerProducer.close();
        }*/

    }

    private static void getAllCustomers() {
        EntityManager entityManagerProducer = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder cb = entityManagerProducer.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);

        cq.select(root);
        TypedQuery<Student> typedQuery = entityManagerProducer.createQuery(cq);
        List<Student> customers;
        try {
            customers = typedQuery.getResultList();
            customers.forEach(System.out::println);
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }/* finally {
            entityManagerProducer.close();
        }*/
    }
}
