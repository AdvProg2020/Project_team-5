package model.productThings;

import model.persons.Seller;

public class SellerRelatedInfoAboutGood {
    private Seller seller;
    private long price;
    private int availableNumber;

    public SellerRelatedInfoAboutGood(Seller seller, long price, int availableNumber) {
        this.seller = seller;
        this.price = price;
        this.availableNumber = availableNumber;
    }

    public Seller getSeller() {
        return seller;
    }

    public long getPrice() {
        return price;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }

    @Override
    public String toString() {
        return "seller = " + seller.getUsername() +
                "\tprice = " + price +
                "\tavailableNumber = " + availableNumber;
    }
}