package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerProducer;
import ApProject_OnlineShop.model.persons.Person;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SqlPersonApi extends SqlAPIs<Person> {
    public SqlPersonApi() {
        super(Person.class);
    }

    public Person getPersonByUsername(String username) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);

        query.select(root).where(criteriaBuilder.equal(root.get("UserName"), username));
        TypedQuery<Person> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    public boolean isExistPersonWithUsername(String username) {
        return getPersonByUsername(username) != null;
    }
}
