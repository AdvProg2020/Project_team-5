package model.requests;

import model.persons.Seller;
import model.productThings.Good;

import static model.productThings.GoodStatus.CONFIRMED;

public class AddingGoodRequest extends Request {
    private Good good;
    private Seller seller;

    public AddingGoodRequest(Good good, Seller seller) {
        this.good = good;
        this.seller = seller;
    }

    @Override
    public void acceptRequest() {
        good.getSubCategory().addGood(this.good);
        if (good.getSellerRelatedInfoAboutGoods().size() > 1){
            good.getSellerRelatedInfoAboutGoods().get(good.getSellerRelatedInfoAboutGoods().size()-1).getSeller().addToActiveGoods(good);
            good.setGoodStatus(CONFIRMED);
        }
    }

    @Override
    public String toString() {
        return "AddingGoodRequest :\n" +
                good.toString() + seller.toString();
    }
}
