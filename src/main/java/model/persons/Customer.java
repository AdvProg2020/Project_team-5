package model.persons;

import model.orders.Order;
import model.persons.Person;
import model.productThings.DiscountCode;

import java.util.ArrayList;

public class Customer extends Person {
    private ArrayList<DiscountCode> discountCodes;
    private ArrayList<Order> previousOrders;
    private long credit;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
    }

    public void addDiscountCode(DiscountCode discountCode) {
        this.discountCodes.add(discountCode);
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        this.discountCodes.remove(discountCode);
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(ArrayList<DiscountCode> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public ArrayList<Order> getPreviousOrders() {
        return previousOrders;
    }

    public void setPreviousOrders(ArrayList<Order> previousOrders) {
        this.previousOrders = previousOrders;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return super.toString() +
                "credit = " + getCredit() + "\n" + "-------------------";
    }
}
