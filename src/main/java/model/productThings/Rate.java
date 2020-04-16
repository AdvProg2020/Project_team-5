package model.productThings;

import model.persons.Customer;

public class Rate {
    private Customer customer;
    private Good good;
    private int rate;

    public Rate(Customer customer, Good good, int rate) {
        this.customer = customer;
        this.good = good;
        this.rate = rate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
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
                , this.customer.getUsername(), this.good.getGoodId(),
                this.good.getName(), this.rate);
    }
}
