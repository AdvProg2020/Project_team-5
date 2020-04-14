package model.productThings;

import model.persons.Seller;

public class GoodInCart {
    private Good good;
    private Seller seller;
    private int number;

    public GoodInCart(Good good, Seller seller, int number) {
        this.good = good;
        this.seller = seller;
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
    public String toString(){
        String good = "name : " + getGood().getName() + "\tbrand : " + getGood().getBrand() + "\tprice before off : " + getGood().getPriceBySeller(getSeller())
                // + "price after off : " + getGood.getPriceAfterOff
                + "\tnumber :" + getNumber() + "\tseller : " + getSeller().getFirstName() + " " + getSeller().getLastName();
        return good;
    }
}