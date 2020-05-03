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

   public Off findOffById(long offId){
      return activeOffs.stream().filter(off -> off.getOffId() == offId).findAny().orElse(null);
   }

   public boolean hasThisOff(long offId){
       return findOffById(offId) != null;
   }

    public Good findProductOfSeller(long productId) {
        return this.activeGoods.stream().filter((good -> good.getGoodId() == productId)).findAny().orElse(null);
    }

    public boolean hasThisProduct(long productId){
        return findProductOfSeller(productId) != null;
    }

    public long balance() {
        return this.previousSells.stream().mapToLong(OrderForSeller::getPrice).sum();
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + company.toString() + "active goods:\n" + activeGoods.toString() + "\n" + "-------------------";
    }
}
