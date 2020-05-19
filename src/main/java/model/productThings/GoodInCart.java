package model.productThings;

import model.Shop;
import model.persons.Seller;

public class GoodInCart {
    private static long goodInCartCounter = 1;
    private long goodInCartId;
    private long goodId;
    private String seller;
    private int number;

    public GoodInCart(Good good, Seller seller, int number) {
        if (seller == null)
            seller = good.getSellerRelatedInfoAboutGoods().get(0).getSeller();
        this.goodId = good.getGoodId();
        this.seller = seller.getUsername();
        this.number = number;
        this.goodInCartId = goodInCartCounter++;
    }

    public long getFinalPrice() {
        return Shop.getInstance().getFinalPriceOfAGood(getGood(), getSeller()) * number;
    }

    public static void setGoodInCartCounter(long goodInCartCounter) {
        GoodInCart.goodInCartCounter = goodInCartCounter;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Good getGood() {
        return Shop.getInstance().findGoodById(goodId);
    }

    public Seller getSeller() {
        return (Seller) Shop.getInstance().findUser(seller);
    }

    public int getNumber() {
        return number;
    }

    public long getGoodInCartId() {
        return goodInCartId;
    }

    @Override
    public String toString() {
        String toString = "name : " + getGood().getName() + "\tbrand : " + getGood().getBrand();
        if (Shop.getInstance().getFinalPriceOfAGood(getGood(), getSeller()) != getGood().getPriceBySeller(getSeller()))
            toString += ("\tprice before off : " + getGood().getPriceBySeller(getSeller())
                    + "\tprice after off : " + Shop.getInstance().getFinalPriceOfAGood(getGood(), getSeller()));
        else
            toString += (("\tprice : " + getGood().getPriceBySeller(getSeller())));
        toString += ("\tnumber :" + getNumber() + "\tseller : " + getSeller().getFirstName() + " " + getSeller().getLastName());
        return toString;
    }
}