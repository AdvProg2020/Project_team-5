package model.persons;

import model.orders.Order;
import model.orders.OrderForSeller;
import model.persons.Person;
import model.productThings.Good;
import model.productThings.Off;

import java.util.ArrayList;

public class Seller extends Person {
    private Company company;
    private ArrayList<OrderForSeller> previousSells;
    private ArrayList<Good> activeGoods;
    private ArrayList<Off> activeOffs;

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ArrayList<OrderForSeller> getPreviousSells() {
        return previousSells;
    }

    public void setPreviousSells(ArrayList<OrderForSeller> previousSells) {
        this.previousSells = previousSells;
    }

    public ArrayList<Good> getActiveGoods() {
        return activeGoods;
    }

    public void addToActiveGoods(Good good) {
        this.activeGoods.add(good);
    }

    public void removeFromActiveGoods(Good good) {
        this.activeGoods.remove(good);
    }

    public ArrayList<Off> getActiveOffs() {
        return activeOffs;
    }

    public void setActiveOffs(ArrayList<Off> activeOffs) {
        this.activeOffs = activeOffs;
    }

    public ArrayList<String> buyersOfAGood(Good good) {
        ArrayList<String> buyers = new ArrayList<>();
        for (OrderForSeller order : previousSells) {
            if (order.getNumberPerGood().containsKey(good)) {
                buyers.add(order.getCustomerName());
            }
        }
        return buyers;
    }

    public long balance() {
        long sum = 0;
        for (OrderForSeller order : this.previousSells) {
            sum += (order.getPrice());
        }
        return sum;
    }

    @Override
    public String toString() {
        return super.toString() + company.toString() + "active goods:\n" + activeGoods.toString() + "\n" + "-------------------";
    }
}
