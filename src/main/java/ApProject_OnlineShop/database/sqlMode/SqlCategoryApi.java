package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerProducer;
import ApProject_OnlineShop.model.category.Category;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SqlCategoryApi extends SqlAPIs<Category>{
    public SqlCategoryApi() {
        super(Category.class);
    }

    public Category getCategoryByName(String categoryName) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = criteriaBuilder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);

        query.select(root).where(criteriaBuilder.equal(root.get("Name"), categoryName));
        TypedQuery<Category> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    public boolean isExistCategoryWithName(String categoryName) {
        return getCategoryByName(categoryName) != null;
    }
}
