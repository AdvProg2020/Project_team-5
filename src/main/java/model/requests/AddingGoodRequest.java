package model.requests;

import model.Shop;
import model.persons.Seller;
import model.productThings.Good;


public class AddingGoodRequest extends Request {
    private Good good;
    private Seller seller;

    public AddingGoodRequest(Good good, Seller seller) {
        this.good = good;
        this.seller = seller;
    }

    @Override
    public void acceptRequest() {

        Good originalGood;
        if ((originalGood = Shop.getInstance().getGoodByNameAndBrandAndSubCategory(good.getName(), good.getBrand(), good.getSubCategory())) == null) {
            good.getSubCategory().addGood(good);
        } else {
            originalGood.addSeller(good.getSellerRelatedInfoAboutGoods().get(0));
        }
        seller.addToActiveGoods(good);
    }

    @Override
    public String toString() {
        return "AddingGoodRequest :\n" +
                "request id = " + super.getRequestId() + "\n" +
                good.toString() + "seller = " + seller.getUsername();
    }
}
