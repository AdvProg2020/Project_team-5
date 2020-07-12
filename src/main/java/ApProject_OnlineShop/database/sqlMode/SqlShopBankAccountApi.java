package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerProducer;
import ApProject_OnlineShop.model.ShopBankAccount;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SqlShopBankAccountApi extends SqlAPIs<ShopBankAccount> {
    public SqlShopBankAccountApi() {
        super(ShopBankAccount.class);
    }

    public ShopBankAccount getShopBankAccountByAccountId(String accountId) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ShopBankAccount> query = criteriaBuilder.createQuery(ShopBankAccount.class);
        Root<ShopBankAccount> root = query.from(ShopBankAccount.class);

        query.select(root).where(criteriaBuilder.equal(root.get("AccountId"), accountId));
        TypedQuery<ShopBankAccount> typedQuery = entityManager.createQuery(query);
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
