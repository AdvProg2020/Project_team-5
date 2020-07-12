package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.model.persons.Customer;

public class SqlCustomerApi extends SqlAPIs<Customer> {
    public SqlCustomerApi() {
        super(Customer.class);
    }
}
