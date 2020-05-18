package model.persons;

import model.orders.OrderForSeller;
import model.productThings.Good;
import model.productThings.Off;

import java.util.ArrayList;

public class Seller extends Person {
    private Company company;
    private ArrayList<OrderForSeller> previousSells;
    private ArrayList<Good> activeGoods;
    private ArrayList<Off> activeOffs;

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, Company company) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.previousSells = new ArrayList<>();
        this.activeGoods = new ArrayList<>();
        this.activeOffs = new ArrayList<>();
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public ArrayList<OrderForSeller> getPreviousSells() {
        return previousSells;
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

    public void addOff(Off off) {
        this.activeOffs.add(off);
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

    public void addOrder(OrderForSeller order) {
        previousSells.add(order);
    }

    public Off findOffById(long offId) {
        return activeOffs.stream().filter(off -> off.getOffId() == offId).findAny().orElse(null);
    }

    public Good findProductOfSeller(long productId) {
        return this.activeGoods.stream().filter((good -> good.getGoodId() == productId)).findAny().orElse(null);
    }

    public boolean hasThisProduct(long productId) {
        return findProductOfSeller(productId) != null;
    }

    public long balance() {
        return this.previousSells.stream().mapToLong(OrderForSeller::getPrice).sum();
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + company.toString() + "\nactive goods:\n" + activeGoods.toString() + "\n" + "-------------------";
    }
}
