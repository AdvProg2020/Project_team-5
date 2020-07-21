package ApProject_OnlineShop.controller.products;

import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.productThings.Auction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuctionsController {
    public List<Massage> getMassages(String auctionId) {
        ArrayList<Massage> massages = new ArrayList<>();
        for (Massage massage : Shop.getInstance().getMassages()) {
            if (massage.getReceiverUserName().equals("auction_" + auctionId))
                massages.add(massage);
        }
        return massages;
    }

    public boolean isCustomerOfferedAPriceInAuction(Person person, int auctionId) {
        Auction auction = Shop.getInstance().findAuctionById(auctionId);
        return auction.getAllCustomersOffers().containsKey(person);
    }

    public long getBestPriceOfAuction(int auctionId) {
        Auction auction = Shop.getInstance().findAuctionById(auctionId);
        if (auction.getAllCustomersOffers().size() > 0) {
            Map<Customer, Long> sortedAuctionOffers =
                    auction.getAllCustomersOffers().entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, ((aLong, aLong2) -> aLong2 - aLong), LinkedHashMap::new));
            Customer winner = null;
            for (Customer customer : sortedAuctionOffers.keySet()) {
                winner = customer;
                break;
            }
            return auction.getAllCustomersOffers().get(winner);
        }
        else
            return 0L;
    }
}
