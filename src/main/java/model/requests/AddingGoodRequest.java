package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.database.Database;
import model.persons.Seller;
import model.productThings.Good;

import java.io.IOException;


public class AddingGoodRequest extends Request {
    private Good good;
    private Seller seller;

    public AddingGoodRequest(Good good, Seller seller) {
        this.good = good;
        this.seller = seller;
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Good originalGood;
        if ((originalGood = Shop.getInstance().getGoodByNameAndBrandAndSubCategory(good.getName(), good.getBrand(), good.getSubCategory())) == null) {
            good.getSubCategory().addGood(good);
        } else {
            originalGood.addSeller(good.getSellerRelatedInfoAboutGoods().get(0));
        }
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        seller.addToActiveGoods(good.getGoodId());
        Shop.getInstance().getHashMapOfGoods().put(good.getGoodId(),good);
        //save subcategory or not ????
        //Database.getInstance().saveItem(good.getSubCategory());
      //  Database.getInstance().saveItem(good.getSubCategory().getParentCategory());
       // Database.getInstance().saveItem(seller);
    }

    @Override
    public String toString() {
        return "AddingGoodRequest :\n" +
                "request id = " + super.getRequestId() + "\n" +
                good.toString() + "\nseller = " + seller.getUsername();
    }
}
