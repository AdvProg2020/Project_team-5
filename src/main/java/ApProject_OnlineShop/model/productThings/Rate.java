package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;

public class Rate {
    private String customer;
    private long good;
    private int rate;

    public Rate(Customer customer, Good good, int rate) {
        this.customer = customer.getUsername();
        this.good = good.getGoodId();
        this.rate = rate;
    }

    public Customer getCustomer() {
        return (Customer) Shop.getInstance().findUser(customer);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer.getUsername();
    }

    public Good getGood() {
        return Shop.getInstance().findGoodById(good);
    }

    public void setGood(Good good) {
        this.good = good.getGoodId();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }



    @Override
    public String toString() {
        return String.format("################\nCustomer Username: %s\n" +
                        "Product Id : %d\nProduct Name : %s\nRate : %d\n################\n"
                , this.customer, this.good,
                this.getGood().getName(), this.rate);
    }
}
