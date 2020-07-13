package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.persons.Seller;

import java.time.LocalDate;
import java.util.HashMap;

public class Auction {
    private static int auctionsCount = 1;
    private int auctionId;
    private long good;
    private String seller;
    private LocalDate startDate;
    private LocalDate endDate;
    private HashMap<String, Long> customersOfferedPrices;

    public Auction(Good good, Seller seller, LocalDate startDate, LocalDate endDate) {
        this.auctionId = auctionsCount++;
        this.good = good.getGoodId();
        this.seller = seller.getUsername();
        this.startDate = startDate;
        this.endDate = endDate;
        this.customersOfferedPrices = new HashMap<>();
    }
}
