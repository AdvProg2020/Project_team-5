package ApProject_OnlineShop.model.persons;

import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Table(name = "Seller")
public class Seller extends Person implements Serializable {
    @ManyToOne
    @JoinColumn(name = "CompanyID", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private ArrayList<OrderForSeller> previousSells;

    @ManyToMany
    @JoinTable(name = "ProductAndSeller", joinColumns = @JoinColumn(name = "SellerId"), inverseJoinColumns = @JoinColumn(name = "ProductID"))
    private ArrayList<Good> activeGoods;

    @Column(name = "BankAccountId")
    private String bankAccountId;

    @Column(name = "Balance", nullable = false)
    private long balance;

    @OneToMany(mappedBy = "seller")
    private ArrayList<Off> activeOffs;

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, Company company) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.previousSells = new ArrayList<>();
        this.activeGoods = new ArrayList<>();
        this.activeOffs = new ArrayList<>();
        this.company = company;
        this.balance = 0;
    }

    public Seller() {
        this.previousSells = new ArrayList<>();
        this.activeGoods = new ArrayList<>();
        this.activeOffs = new ArrayList<>();
    }

    public Company getCompany() {
        return this.company;
    }

    public ArrayList<OrderForSeller> getPreviousSells() {
        return this.previousSells;
        /*
        ArrayList<OrderForSeller> ordersForSeller= new ArrayList<>();
        for (Long id : this.previousSellsIds) {
            ordersForSeller.add((OrderForSeller) Shop.getInstance().getHasMapOfOrders().get(id));
        }
        return ordersForSeller;

         */
    }

    public ArrayList<Good> getActiveGoods() {
        return this.activeGoods;
        /*
        ArrayList<Good> activeGoods= new ArrayList<>();
        for (Long id : this.activeGoodsIds) {
            activeGoods.add(Shop.getInstance().getHashMapOfGoods().get(id));
        }
        return activeGoods;

         */
    }

    public void addToActiveGoods(Good good) {
        this.activeGoods.add(good);
        //this.activeGoodsIds.add(id);
    }

    public void removeFromActiveGoods(Good good) {
        this.activeGoods.remove(good);
        //this.activeGoodsIds.remove(id);
    }

    public void removeFromActiveOffs(Off off) {
        this.activeOffs.remove(off);
        //this.activeOffsIds.remove(id);
    }

    public ArrayList<Off> getActiveOffs() {
        return this.activeOffs;
        /*
        ArrayList<Off> offs=new ArrayList<>();
        for (Long offsId : this.activeOffsIds) {
            offs.add(Shop.getInstance().getHashMapOfOffs().get(offsId));
        }
        return offs;

         */
    }

    public void addOff(Off off) {
        this.activeOffs.add(off);
        //this.activeOffsIds.add(id);
    }

    public ArrayList<String> buyersOfAGood(Good good) {
        ArrayList<String> buyers = new ArrayList<>();
        for (OrderForSeller order : getPreviousSells()) {
            if (order.getNumberPerGood().containsKey(good)) {
                buyers.add(order.getCustomerName());
            }
        }
        return buyers;
    }

    public OrderForSeller findOrderById(long id){
        for (OrderForSeller order : getPreviousSells()) {
            if (order.getOrderId() == id){
                return order;
            }
        }
        return null;
    }

    public void addOrder(OrderForSeller order) {
        previousSells.add(order);
        //previousSellsIds.add(order.getId());
    }

    public Off findOffById(long offId) {
        return getActiveOffs().stream().filter(off -> off.getOffId() == offId).findAny().orElse(null);
    }

    public Good findProductOfSeller(long productId) {
        return this.getActiveGoods().stream().filter((good -> good.getGoodId() == productId)).findAny().orElse(null);
    }

    public boolean hasThisProduct(long productId) {
        return findProductOfSeller(productId) != null;
    }

    public long balance() {
        return this.getPreviousSells().stream().mapToLong(OrderForSeller::getPrice).sum();
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPreviousSells(ArrayList<OrderForSeller> previousSells) {
        this.previousSells = previousSells;
    }

    public void setActiveGoods(ArrayList<Good> activeGoods) {
        this.activeGoods = activeGoods;
    }

    public void setActiveOffs(ArrayList<Off> activeOffs) {
        this.activeOffs = activeOffs;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + getCompany().toString() + "\nactive goods:\n" + getActiveGoods().toString() + "\n" + "-------------------";
    }
}
