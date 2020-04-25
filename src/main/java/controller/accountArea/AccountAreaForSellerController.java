package controller.accountArea;

import controller.MainController;
import exceptions.NotHaveThisProduct;
import model.Shop;
import model.persons.Seller;
import model.productThings.Good;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAreaForSellerController extends AccountAreaController {

    public void removeProduct(long productId) throws NotHaveThisProduct {
        Seller seller = (Seller) MainController.getInstance().getCurrentPerson();
        Good good = seller.findProductOfSeller(productId);
        if (good != null) {
            if (good.getSellerRelatedInfoAboutGoods().size() == 1)
                good.getSubCategory().deleteGood(good);
            else {
                good.removeSeller(seller);
                seller.removeFromActiveGoods(good);
            }
        }
        if (good == null)
            throw new NotHaveThisProduct();
    }

    public String getCompanyInfo(){
        return ((Seller)MainController.getInstance().getCurrentPerson()).getCompany().toString();
    }

    public List<String> getSalesLog(){
        return ((Seller)MainController.getInstance().getCurrentPerson()).getPreviousSells().stream().
                map(orderForSeller -> orderForSeller.toString()).collect(Collectors.toList());
    }

    public long viewBalance(){
        return ((Seller)MainController.getInstance().getCurrentPerson()).balance();
    }

    public ArrayList<String> buyersOfProduct(long productId) {
        return ((Seller) MainController.getInstance().getCurrentPerson()).buyersOfAGood(Shop.getInstance().findGoodById(productId));
    }


}
