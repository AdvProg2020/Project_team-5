package model.requests;

import database.Database;
import exception.FileCantBeSavedException;
import model.Shop;
import model.category.SubCategory;
import model.persons.Seller;
import model.productThings.Good;

import java.io.IOException;
import java.util.HashMap;


public class AddingGoodRequest extends Request {
    private String nameOfGood;
    private String brandOfGood;
    private String subCategoryOfGood;
    private String detailsOfGood;
    private HashMap<String, String> categoryPropertiesOfGood;
    private long priceOfGood;
    private int availableNumberOfGood;
    private String seller;

    public AddingGoodRequest(String nameOfGood, String brandOfGood, SubCategory subCategoryOfGood, String detailsOfGood,
                             HashMap<String, String> categoryPropertiesOfGood, long priceOfGood, int availableNumberOfGood, String seller) {
        this.nameOfGood = nameOfGood;
        this.brandOfGood = brandOfGood;
        this.subCategoryOfGood = subCategoryOfGood.getName();
        this.detailsOfGood = detailsOfGood;
        this.categoryPropertiesOfGood = categoryPropertiesOfGood;
        this.priceOfGood = priceOfGood;
        this.availableNumberOfGood = availableNumberOfGood;
        this.seller = seller;
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Good good = new Good(nameOfGood, brandOfGood, Shop.getInstance().findSubCategoryByName(subCategoryOfGood), detailsOfGood,
                categoryPropertiesOfGood, (Seller) Shop.getInstance().findUser(seller), priceOfGood, availableNumberOfGood);
        Good originalGood;
        Seller seller1 = (Seller) Shop.getInstance().findUser(seller);
        if ((originalGood = Shop.getInstance().getGoodByNameAndBrandAndSubCategory(good.getName(), good.getBrand(), good.getSubCategory())) == null) {
            good.getSubCategory().addGood(good);
            good.setGoodStatus(Good.GoodStatus.CONFIRMED);
            seller1.addToActiveGoods(good.getGoodId());
            Database.getInstance().saveItem(good.getSubCategory());
            Database.getInstance().saveItem(good.getSellerRelatedInfoAboutGoods().get(0), good.getGoodId());
            Shop.getInstance().getHashMapOfGoods().put(good.getGoodId(), good);
            Database.getInstance().saveItem(good);
        } else {
            originalGood.addSeller(good.getSellerRelatedInfoAboutGoods().get(0));
            seller1.addToActiveGoods(originalGood.getGoodId());
            Database.getInstance().saveItem(good.getSellerRelatedInfoAboutGoods().get(0), originalGood.getGoodId());
            Database.getInstance().saveItem(originalGood);
        }
        Shop.getInstance().addSellerRelatedInfoAboutGood(good.getSellerRelatedInfoAboutGoods().get(0));
        Database.getInstance().saveItem(seller1);
    }

    @Override
    public String toString() {
        return "AddingGoodRequest :\n" +
                "request id = " + super.getRequestId() + "\n" +
                "------------------------------------\n"
                + "\nname = " + nameOfGood
                + "\nbrand = " + brandOfGood
                + "\ncategory = " + Shop.getInstance().findSubCategoryByName(subCategoryOfGood).getParentCategory().getName()
                + "\nsubcategory = " + subCategoryOfGood
                + "details =\n" + detailsOfGood +
                "------------------------------------" + "\nseller = " + seller;
    }
}
