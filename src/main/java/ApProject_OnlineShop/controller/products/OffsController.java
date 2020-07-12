package ApProject_OnlineShop.controller.products;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.exception.productExceptions.ThisProductIsnotInAnyOff;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;


import java.util.List;

public class OffsController {

//    public String showOffProducts() {
//        List<Good> goodList2 = MainController.getInstance().getControllerForFiltering().showProducts();
//        List<Good> goodList = MainController.getInstance().getControllerForSorting().showProducts(goodList2);
//        String output = "";
//        for (Good good : goodList) {
//            if(good == null)
//                continue;
//            if (goodList.get(goodList.size() - 1).equals(good))
//                output += getGoodDetail(good);
//            else
//                output += (getGoodDetail(good) + "\n");
//        }
//        return output;
//    }

//    private String getGoodDetail(Good good) {
//        String output = "";
//        Seller seller = good.getSellerThatPutsThisGoodOnOff();
//        output += (good.getName() + " =      " + "\tgood id : " + good.getGoodId() + "\t         price before off : "
//                + good.getPriceBySeller(seller)
//                + "              \t price after off : " + Shop.getInstance().getFinalPriceOfAGood(good, seller));
//        return output;
//    }

//    public void showAProduct(long id) throws ProductWithThisIdNotExist, ThisProductIsnotInAnyOff {
//        if (Shop.getInstance().findGoodById(id) == null) {
//            throw new ProductWithThisIdNotExist();
//        } else if (!Shop.getInstance().getOffGoods().contains(Shop.getInstance().findGoodById(id))) {
//            throw new ThisProductIsnotInAnyOff();
//        } else {
//            MainController.getInstance().getProductController().setGood(Shop.getInstance().findGoodById(id));
//        }
//    }
}
