package view.accountArea;

import controller.MainController;
import model.Shop;
import model.persons.Seller;
import model.productThings.Good;
import view.Menu;
import exception.*;

import java.util.ArrayList;

public class AccountAreaForSeller extends Menu {

    public AccountAreaForSeller(Menu parentMenu) {
        super("Account area for seller", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("-view company information");
        this.commandNames.add("-view sales history");
        this.commandNames.add("-add product");
        this.commandNames.add("-remove product");
        this.commandNames.add("-show categories");
        this.commandNames.add("-view balance");
    }



    @Override
    public void execute() {
        int chosenCommand = Integer.parseInt(getInput());
    }



    public void removeProduct(long productId) throws ProductNotFoundException{
        Seller seller = (Seller)MainController.getInstance().getCurrentPerson();
        Good good = seller.findProductOfSeller(productId);
        if (good != null){
            if (good.getSellerRelatedInfoAboutGoods().size()==1)
                good.getSubCategory().deleteGood(good);
            else {
                good.removeSeller(seller);
                seller.removeFromActiveGoods(good);
            }
        }
        if (good== null)
            throw new ProductNotFoundException();
    }

    public ArrayList<String> buyersOfProduct(long productId){
        return ((Seller)MainController.getInstance().getCurrentPerson()).buyersOfAGood(Shop.getInstance().findGoodById(productId));
    }

}