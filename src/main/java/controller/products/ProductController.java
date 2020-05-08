package controller.products;

import exception.DontHaveEnoughNumberOfThisProduct;
import model.Shop;
import model.productThings.Good;
import model.productThings.SellerRelatedInfoAboutGood;

public class ProductController {
    private Good good;

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
        good.setSeenNumber(good.getSeenNumber() + 1);
    }

    public String digest() {
        return good.toString();
    }

    public String getSellersOfAGood() {
        String output = "";
        int i = 1;
        for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
            if (i < good.getSellerRelatedInfoAboutGoods().size())
                output += ((i++) + "-" + infoAboutGood.getSeller().getUsername() + "\n");
            else
                output += ((i++) + "-" + infoAboutGood.getSeller().getUsername());
        }
        return output;
    }

    public int numbersOfSellers(){
        return good.getSellerRelatedInfoAboutGoods().size();
    }

    public void addGoodToCart(int number,int sellerNumber) throws DontHaveEnoughNumberOfThisProduct {
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood=good.getSellerRelatedInfoAboutGoods().get(sellerNumber-1);
        if(sellerRelatedInfoAboutGood.getAvailableNumber()< number)
            throw new DontHaveEnoughNumberOfThisProduct();
        Shop.getInstance().addGoodToCart(good,sellerRelatedInfoAboutGood.getSeller(),number);
    }

    public int getAvailableNumberOfAProductByASeller(int sellerNumber){
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood=good.getSellerRelatedInfoAboutGoods().get(sellerNumber-1);
        return sellerRelatedInfoAboutGood.getAvailableNumber();
    }
}
