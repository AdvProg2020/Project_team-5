package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class Auction {
    private static int auctionsCount = 1;
    private int auctionId;
    private String title;
    private String description;
    private long good;
    private String seller;
    private LocalDate startDate;
    private LocalDate endDate;
    private HashMap<String, Long> customersOfferedPrices;

    public Auction(Good good, Seller seller, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.auctionId = auctionsCount++;
        this.title = title;
        this.description = description;
        this.good = good.getGoodId();
        this.seller = seller.getUsername();
        this.startDate = startDate;
        this.endDate = endDate;
        this.customersOfferedPrices = new HashMap<>();
    }

    public static int getAuctionsCount() {
        return auctionsCount;
    }

    public static void setAuctionsCount(int auctionsCount) {
        Auction.auctionsCount = auctionsCount;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public Good getGood() {
        return Shop.getInstance().findGoodById(good);
    }

    public void setGood(Good good) {
        this.good = good.getGoodId();
    }

    public Seller getSeller() {
        return (Seller) Shop.getInstance().findUser(seller);
    }

    public void setSeller(Seller seller) {
        this.seller = seller.getUsername();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addOffer(Customer customer, long price) throws IOException, FileCantBeSavedException {
        this.customersOfferedPrices.put(customer.getUsername(), price);
        Database.getInstance().saveItem(this);
    }

    public void removeOffer(Customer customer) throws IOException, FileCantBeSavedException {
        this.customersOfferedPrices.remove(customer.getUsername());
        Database.getInstance().saveItem(this);
    }

    public HashMap<Customer, Long> getAllCustomersOffers() {
        HashMap<Customer, Long> customerOffers = new HashMap<>();
        for (String username : this.customersOfferedPrices.keySet()) {
            customerOffers.put((Customer) Shop.getInstance().findUser(username), this.customersOfferedPrices.get(username));
        }
        return customerOffers;
    }
}
