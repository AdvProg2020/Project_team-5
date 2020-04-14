package model.persons;

import model.orders.Order;
import model.persons.Person;
import model.productThings.Good;
import model.productThings.Off;

import java.util.ArrayList;

public class Seller extends Person {
    private Company company;
    private ArrayList<Order> previousSells;
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

    public ArrayList<Order> getPreviousSells() {
        return previousSells;
    }

    public void setPreviousSells(ArrayList<Order> previousSells) {
        this.previousSells = previousSells;
    }

    public ArrayList<Good> getActiveGoods() {
        return activeGoods;
    }

    public void setActiveGoods(ArrayList<Good> activeGoods) {
        this.activeGoods = activeGoods;
    }

    public ArrayList<Off> getActiveOffs() {
        return activeOffs;
    }

    public void setActiveOffs(ArrayList<Off> activeOffs) {
        this.activeOffs = activeOffs;
    }

    @Override
    public String toString() {
        return super.toString() + company.toString() + "active goods: \n" + activeGoods.toString() + "\n" + "-------------------";
    }
}
