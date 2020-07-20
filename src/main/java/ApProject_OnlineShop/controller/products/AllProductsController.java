package ApProject_OnlineShop.controller.products;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.productThings.FileProduct;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllProductsController {

//    public String showCategories() {
//        ArrayList<Category> allCategories = Shop.getInstance().getAllCategories();
//        String output = "";
//        for (Category category : allCategories) {
//            output += category.toString();
//        }
//        return output;
//    }

//    public void showAProduct(long id) throws ProductWithThisIdNotExist {
//        if (Shop.getInstance().findGoodById(id) == null) {
//            throw new ProductWithThisIdNotExist();
//        } else {
//            MainController.getInstance().getProductController().setGood(Shop.getInstance().findGoodById(id));
//        }
//    }

//    public String showProducts() {
//        List<Good> goodList2 = MainController.getInstance().getControllerForFiltering().showProducts();
//        List<Good> goodList = MainController.getInstance().getControllerForSorting().showProducts(goodList2);
//        String output = "";
//        for (Good good : goodList) {
//            if (goodList.get(goodList.size() - 1).equals(good))
//                output += good.toString();
//            else
//                output += (good.toString() + "\n");
//        }
//        return output;
//    }

    public List<String> getProductBrief(long productId) {
        Good good = Shop.getInstance().findGoodById(productId);
        ArrayList<String> goodInfo = new ArrayList<>();
        goodInfo.add(good.getName() + " " + good.getBrand());
        goodInfo.add("" + (good.getAverageRate() / (float) 2));
        goodInfo.add("" + good.getMinimumPrice() + " Rials");
        return goodInfo;
    }

    public List<String> getFileProductBrief(long fileProductId) {
        FileProduct fileProduct = Shop.getInstance().findFileProductById(fileProductId);
        ArrayList<String> goodInfo = new ArrayList<>();
        goodInfo.add(fileProduct.getName());
        goodInfo.add("" + fileProduct.getDownloadNumber());
        goodInfo.add("" + fileProduct.getPrice() + " Rials");
        return goodInfo;
    }

    public List<String> getOffProductBriefSummery(long productId) {
        Good good = Shop.getInstance().findGoodById(productId);
        ArrayList<String> goodInfo = new ArrayList<>();
        goodInfo.add(good.getName() + " " + good.getBrand());
        goodInfo.add("" + (good.getAverageRate() / (float) 2));
        goodInfo.add("" + good.getPriceBySeller(good.getSellerThatPutsThisGoodOnOff()));
        goodInfo.add("" + Shop.getInstance().getFinalPriceOfAGood(good, good.getSellerThatPutsThisGoodOnOff()));
        return goodInfo;
    }

    public List<String> getAllCategories(){
        return Shop.getInstance().getAllCategories().stream().map(category -> category.getName()).collect(Collectors.toList());
    }

    public List<Long> getGoods(long id){
        return Server.getControllerForSortingHashMap().get(id).showProducts(Server.getControllerForFilteringHashMap().get(id).showProducts()).
                stream().map(Good::getGoodId).collect(Collectors.toList());
    }

    public List<Long> getFileProducts() {
        return Shop.getInstance().getAllFileProductsList().stream().map(FileProduct::getFileProductId).collect(Collectors.toList());
    }

    public boolean isInOff(long goodId){
        return Shop.getInstance().findGoodById(goodId).getSellerThatPutsThisGoodOnOff() != null;
    }
}
