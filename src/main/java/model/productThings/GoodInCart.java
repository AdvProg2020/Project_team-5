package model.productThings;

import model.Shop;
import model.persons.Seller;

public class GoodInCart {
    private Good good;
    private Seller seller;
    private int number;

    public GoodInCart(Good good, Seller seller, int number) {
        if (seller == null)
            seller = good.getSellerRelatedInfoAboutGoods().get(0).getSeller();
        this.good = good;
        this.seller = seller;
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Good getGood() {
        return good;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        String toString = "name : " + getGood().getName() + "\tbrand : " + getGood().getBrand();
        if (Shop.getInstance().getFinalPriceOfAGood(good, seller) != good.getPriceBySeller(seller))
            toString += ("\tprice before off : " + getGood().getPriceBySeller(getSeller())
                    + "price after off : " + Shop.getInstance().getFinalPriceOfAGood(good, seller));
        else
            toString += (("\tprice : " + getGood().getPriceBySeller(getSeller())));
        toString += ("\tnumber :" + getNumber() + "\tseller : " + getSeller().getFirstName() + " " + getSeller().getLastName());
        return toString;
    }
}