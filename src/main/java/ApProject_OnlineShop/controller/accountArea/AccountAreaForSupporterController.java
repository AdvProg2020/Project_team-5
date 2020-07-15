package ApProject_OnlineShop.controller.accountArea;

import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.Shop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AccountAreaForSupporterController extends AccountAreaController {
    public List<String> getCustomersChat(String supporter){
        HashSet<String> customers = new HashSet<>();
        for (Massage massage : Shop.getInstance().getMassages()) {
            if (massage.getReceiverUserName().equals(supporter))
                customers.add(massage.getSenderUserName());
        }
        return new ArrayList<>(customers);
    }
}
