package ApProject_OnlineShop.model.persons;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.productThings.Auction;
import ApProject_OnlineShop.model.productThings.FileProduct;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;

import java.io.IOException;
import java.util.ArrayList;

public class Seller extends Person {
    private long company;
    private ArrayList<Long> previousSellsIds;
    private ArrayList<Long> activeGoodsIds;
    private ArrayList<Long> activeOffsIds;
    private ArrayList<Integer> activeAuctions;
    private ArrayList<Long> activeFileProducts;
    private String bankAccountId;
    private long balance;

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, Company company) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.previousSellsIds = new ArrayList<>();
        this.activeGoodsIds = new ArrayList<>();
        this.activeOffsIds = new ArrayList<>();
        this.activeAuctions = new ArrayList<>();
        this.activeFileProducts = new ArrayList<>();
        this.company = company.getId();
    }

    public void setBalance(long balance) {
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
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException | FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public Company getCompany() {
        return Shop.getInstance().getAllCompanies().get(company);
    }

    public ArrayList<OrderForSeller> getPreviousSells() {
        ArrayList<OrderForSeller> ordersForSeller= new ArrayList<>();
        for (Long id : this.previousSellsIds) {
            ordersForSeller.add((OrderForSeller) Shop.getInstance().getHasMapOfOrders().get(id));
        }
        return ordersForSeller;
    }

    public ArrayList<Good> getActiveGoods() {
        ArrayList<Good> activeGoods= new ArrayList<>();
        for (Long id : this.activeGoodsIds) {
            activeGoods.add(Shop.getInstance().getHashMapOfGoods().get(id));
        }
        return activeGoods;
    }

    public void addToActiveGoods(long id) {
        this.activeGoodsIds.add(id);
    }

    public void removeFromActiveGoods(long id) {
        this.activeGoodsIds.remove(id);
    }

    public void removeFromActiveOffs(long id) { this.activeOffsIds.remove(id); }

    public ArrayList<Off> getActiveOffs() {
        ArrayList<Off> offs=new ArrayList<>();
        for (Long offsId : this.activeOffsIds) {
            offs.add(Shop.getInstance().getHashMapOfOffs().get(offsId));
        }
        return offs;
    }

    public void addOff(long id) {
        this.activeOffsIds.add(id);
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
        previousSellsIds.add(order.getOrderId());
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

    @Override
    public String toString() {
        return super.toString() + "\n" + getCompany().toString() + "\nactive goods:\n" + getActiveGoods().toString() + "\n" + "-------------------";
    }
}
