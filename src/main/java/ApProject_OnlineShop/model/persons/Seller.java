package ApProject_OnlineShop.model.persons;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.productThings.Auction;
import ApProject_OnlineShop.model.productThings.FileProduct;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;

import javax.persistence.*;
import java.io.Serializable;
import java.io.IOException;
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

    @Transient
    private ArrayList<Integer> activeAuctions;
    @Transient
    private ArrayList<Long> activeFileProducts;

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, Company company) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.previousSells = new ArrayList<>();
        this.activeGoods = new ArrayList<>();
        this.activeOffs = new ArrayList<>();
        this.activeAuctions = new ArrayList<>();
        this.activeFileProducts = new ArrayList<>();
        this.company = company;
        this.balance = 0;
    }

    public Seller() {
        this.previousSells = new ArrayList<>();
        this.activeGoods = new ArrayList<>();
        this.activeOffs = new ArrayList<>();
    }

   /* public void setBalance(long balance) {
        this.balance = balance;
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }

    public long getBalance() {
        return balance;
    }*/

    /*public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException | FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }

    public String getBankAccountId() {
        return bankAccountId;
    }*/

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

    public ArrayList<FileProduct> getActiveFileProducts() {
        ArrayList<FileProduct> fileProducts = new ArrayList<>();
        for (Long fileProduct : this.activeFileProducts) {
            fileProducts.add(Shop.getInstance().findFileProductById(fileProduct));
        }
        return fileProducts;
    }

    public void addFileProduct(FileProduct fileProduct) {
        this.activeFileProducts.add(fileProduct.getFileProductId());
    }

    public void removeActiveFileProduct(FileProduct fileProduct) {
        int index = 0;
        for (;index < this.activeFileProducts.size();index++) {
            if (this.activeFileProducts.get(index) == fileProduct.getFileProductId())
                break;
        }
        this.activeFileProducts.remove(index);
    }

    public ArrayList<Auction> getActiveAuctions() {
        ArrayList<Auction> auctions = new ArrayList<>();
        for (Integer auction : this.activeAuctions) {
            auctions.add(Shop.getInstance().findAuctionById(auction));
        }
        return auctions;
    }

    public void addAuction(Auction auction) {
        this.activeAuctions.add(auction.getAuctionId());
    }

    public void removeAuction(Auction auction) {
        int index = 0;
        for (;index < this.activeAuctions.size();index++) {
            if (this.activeAuctions.get(index) == auction.getAuctionId())
                break;
        }
        this.activeAuctions.remove(index);
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
