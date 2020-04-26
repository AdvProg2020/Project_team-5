package controller.accountArea;

import controller.MainController;
import exception.ProductNotFoundException;
import model.Shop;
import model.persons.Seller;
import model.productThings.Good;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAreaForSellerController extends AccountAreaController {

    public void removeProduct(long productId) throws ProductNotFoundException {
        Seller seller = (Seller) MainController.getInstance().getCurrentPerson();
        Good good = seller.findProductOfSeller(productId);
        if (seller.hasThisProduct(productId))
            throw new ProductNotFoundException();
        if (good.getSellerRelatedInfoAboutGoods().size() == 1)
            good.getSubCategory().deleteGood(good);
        else {
            good.removeSeller(seller);
            seller.removeFromActiveGoods(good);
        }
    }

    public String getCompanyInfo() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).getCompany().toString();
    }

    public List<String> getSalesLog() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).getPreviousSells().stream().
                map(orderForSeller -> orderForSeller.toString()).collect(Collectors.toList());
    }

    public long viewBalance() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).balance();
    }

    public ArrayList<String> buyersOfProduct(long productId) throws ProductNotFoundException {
        if (!((Seller) MainController.getInstance().getCurrentPerson()).hasThisProduct(productId))
            throw new ProductNotFoundException();
        return ((Seller) MainController.getInstance().getCurrentPerson()).buyersOfAGood(Shop.getInstance().findGoodById(productId));
    }

    public String viewProduct(long productId) throws ProductNotFoundException {
        if (!((Seller) MainController.getInstance().getCurrentPerson()).hasThisProduct(productId))
            throw new ProductNotFoundException();
        return ((Seller) MainController.getInstance().getCurrentPerson()).findProductOfSeller(productId).toString();
    }

    public String viewOff(long offId) throws ProductNotFoundException{
        if (!((Seller) MainController.getInstance().getCurrentPerson()).hasThisProduct(offId))
            throw new ProductNotFoundException();
        return ((Seller) MainController.getInstance().getCurrentPerson()).findOffById(offId).toString();
    }
}
