package testThings;

import model.Shop;

public class TestShop {
    public static void clearShop(){
        Shop.getInstance().getHashMapOfCategories().clear();
        Shop.getInstance().getHashMapOfOffs().clear();
        Shop.getInstance().getAllPersons().clear();
        Shop.getInstance().getAllRequest().clear();
        Shop.getInstance().getHashMapOfDiscountCodes().clear();
        Shop.getInstance().getAllRates().clear();
        Shop.getInstance().getAllGoodInCarts().clear();
        Shop.getInstance().getHashMapOfGoods().clear();
        Shop.getInstance().getHasMapOfOrders().clear();
        Shop.getInstance().getAllSubCategories().clear();
        Shop.getInstance().getAllComments().clear();
        Shop.getInstance().getAllCompanies().clear();
        Shop.getInstance().getAllSellerRelatedInfoAboutGood().clear();
    }
}
