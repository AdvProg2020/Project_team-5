package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Rate")
public class Rate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RateID", nullable = false, unique = true)
    private int rateId;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Good good;

    @Column(name = "Rate", nullable = false)
    private int rate;

    public Rate(Customer customer, Good good, int rate) {
        this.customer = customer;
        this.good = good;
        this.rate = rate;
    }

    public Rate() {
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        //this.customer = customer.getUsername();
    }

    public Good getGood() {
        return this.good;
    }

    public void setGood(Good good) {
        this.good = good;
        //this.good = good.getGoodId();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    @Override
    public String toString() {
        return String.format("################\nCustomer Username: %s\n" +
                        "Product Id : %d\nProduct Name : %s\nRate : %d\n################\n"
                , this.customer, this.good,
                this.getGood().getName(), this.rate);
    }
}
