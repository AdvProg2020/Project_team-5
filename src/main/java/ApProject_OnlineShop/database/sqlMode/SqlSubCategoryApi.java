package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerProducer;
import ApProject_OnlineShop.model.category.SubCategory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SqlSubCategoryApi extends SqlAPIs<SubCategory> {
    public SqlSubCategoryApi() {
        super(SubCategory.class);
    }

    public SubCategory getSubCategoryByName(String subCategoryName) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubCategory> query = criteriaBuilder.createQuery(SubCategory.class);
        Root<SubCategory> root = query.from(SubCategory.class);

        query.select(root).where(criteriaBuilder.equal(root.get("Name"), subCategoryName));
        TypedQuery<SubCategory> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(SubCategory targetObject) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            int id = -1;
            if (targetObject != null)
                id = (int) entityManagerFactory.getPersistenceUnitUtil().getIdentifier(targetObject);
            if (id == 0)
                entityManager.persist(targetObject);
            else if (id != -1)
                entityManager.merge(targetObject);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null)
                entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public boolean isExistSubCategoryWithName(String subCategoryName) {
        return getSubCategoryByName(subCategoryName) != null;
    }
}
