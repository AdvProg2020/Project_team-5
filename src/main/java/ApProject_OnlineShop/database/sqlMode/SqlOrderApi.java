package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.SqlAPIs;
import ApProject_OnlineShop.model.orders.Order;

public class SqlOrderApi extends SqlAPIs<Order> {
    public SqlOrderApi() {
        super(Order.class);
    }
}
