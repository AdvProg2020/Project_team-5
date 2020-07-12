package ApProject_OnlineShop.database.sqlMode;

import java.util.HashMap;

public class SqlApiContainer {
    HashMap<String, SqlAPIs> apisList;

    public SqlApiContainer() {
        apisList = new HashMap<>();
        apisList.put("category", new SqlCategoryApi());
        apisList.put("comment", new SqlCommentApi());
        apisList.put("customer", new SqlCustomerApi());
        apisList.put("discount", new SqlDiscountCodeApi());
        apisList.put("good", new SqlGoodApi());
        apisList.put("goodInCart", new SqlGoodInCartApi());
        apisList.put("manager", new SqlManagerApi());
        apisList.put("off", new SqlOffApi());
        apisList.put("order", new SqlOrderApi());
        apisList.put("orderForCustomer", new SqlOrderForCustomerApi());
        apisList.put("orderForSeller", new SqlOrderForSellerApi());
        apisList.put("person", new SqlPersonApi());
        apisList.put("rate", new SqlRateApi());
        apisList.put("seller", new SqlSellerApi());
        apisList.put("sellerRelatedInfoAboutGood", new SqlSellerRelatedInfoAboutGood());
        apisList.put("subCategory", new SqlSubCategoryApi());
    }

    public SqlAPIs<?> getSqlApi(String name) {
        return this.apisList.get(name);
    }
}
