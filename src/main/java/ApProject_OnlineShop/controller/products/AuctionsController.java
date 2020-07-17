package ApProject_OnlineShop.controller.products;

import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.productThings.Auction;

import java.util.ArrayList;
import java.util.List;

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
}
