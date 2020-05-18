package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.category.SubCategory;
import model.database.Database;
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
        if ((originalGood = Shop.getInstance().getGoodByNameAndBrandAndSubCategory(good.getName(), good.getBrand(), good.getSubCategory())) == null) {
            good.getSubCategory().addGood(good);
        } else {
            originalGood.addSeller(good.getSellerRelatedInfoAboutGoods().get(0));
        }
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Seller seller1 = (Seller) Shop.getInstance().findUser(seller);
        seller1.addToActiveGoods(good.getGoodId());
        Shop.getInstance().getHashMapOfGoods().put(good.getGoodId(), good);
        //save subcategory or not ????
        //Database.getInstance().saveItem(good.getSubCategory());
        //  Database.getInstance().saveItem(good.getSubCategory().getParentCategory());
        // Database.getInstance().saveItem(seller);
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
