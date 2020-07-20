package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerProducer;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.productThings.Rate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SqlRateApi extends SqlAPIs<Rate> {
    public SqlRateApi() {
        super(Rate.class);
    }

    public Rate getRateByCustomerAndGood(long goodId, long customerId) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rate> query = criteriaBuilder.createQuery(Rate.class);
        Root<Rate> root = query.from(Rate.class);
        Predicate goodPredicate = criteriaBuilder.equal(root.get("ProductID"), goodId);
        Predicate customerPredicate = criteriaBuilder.equal(root.get("CustomerId"), customerId);
        query.select(root).where(criteriaBuilder.and(goodPredicate, customerPredicate));
        TypedQuery<Rate> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }
}
