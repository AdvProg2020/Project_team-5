package model.orders;

import model.productThings.GoodInCart;

import java.util.ArrayList;

public class OrderForCustomer extends Order {
    private ArrayList<GoodInCart> goodsDetails ;
    private long discountAmount;
    private String address;
    private String phoneNumber;

    public OrderForCustomer(ArrayList<GoodInCart> goodDetails, String address, String phoneNumber) {
        this.goodsDetails = goodDetails;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<GoodInCart> getGoodsDetails() {
        return goodsDetails;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {

        String customerLog = "--------------------------------------------------------------------------------" +
                "\nOrderId : " + this.getOrderId() +
                "\nDate : " + this.getDate() +
                "\nGoodsList :";
        for (GoodInCart goodInfo : getGoodsDetails()) {
            customerLog += ("\n" + goodInfo.toString());
        }

        customerLog += "\nPaid price : " + this.getPrice() +
                "\nDiscount amount : " + this.getDiscountAmount() +
                "\nAddress : " + this.getAddress() +
                "\nPhoneNumber : " + this.getPhoneNumber() +
                "\nOrder status : " + this.getOrderStatus() +
                "--------------------------------------------------------------------------------";

        return customerLog;
    }
}