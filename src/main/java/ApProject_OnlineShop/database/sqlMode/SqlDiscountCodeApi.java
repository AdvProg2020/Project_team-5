package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerProducer;
import ApProject_OnlineShop.model.productThings.DiscountCode;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SqlDiscountCodeApi extends SqlAPIs<DiscountCode> {
    public SqlDiscountCodeApi() {
        super(DiscountCode.class);
    }

    public DiscountCode getDiscountCodeByCode(String code) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DiscountCode> query = criteriaBuilder.createQuery(DiscountCode.class);
        Root<DiscountCode> root = query.from(DiscountCode.class);

        query.select(root).where(criteriaBuilder.equal(root.get("DiscountCode"), code));
        TypedQuery<DiscountCode> typedQuery = entityManager.createQuery(query);
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
