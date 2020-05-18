package model.productThings;

import model.Shop;
import model.persons.Seller;

public class SellerRelatedInfoAboutGood {
    private static long sellerRelatedInfoAboutGoodCount = 1;
    private long sellerRelatedInfoAboutGoodId;
    private String seller;
    private long price;
    private int availableNumber;

    public SellerRelatedInfoAboutGood(Seller seller, long price, int availableNumber) {
        this.sellerRelatedInfoAboutGoodId = sellerRelatedInfoAboutGoodCount++;
        this.seller = seller.getUsername();
        this.price = price;
        this.availableNumber = availableNumber;
    }

    public Seller getSeller() {
        return (Seller) Shop.getInstance().findUser(this.seller);
    }

    public static void setSellerRelatedInfoAboutGoodCount(long sellerRelatedInfoAboutGoodCount) {
        SellerRelatedInfoAboutGood.sellerRelatedInfoAboutGoodCount = sellerRelatedInfoAboutGoodCount;
    }

    public long getSellerRelatedInfoAboutGoodId() {
        return sellerRelatedInfoAboutGoodId;
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
        return "seller = " + seller +
                "\tprice = " + price +
                "\tavailableNumber = " + availableNumber;
    }
}