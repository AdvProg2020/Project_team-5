package model.orders;

import controller.MainController;
import model.Shop;
import model.productThings.GoodInCart;

import java.util.ArrayList;

public class OrderForCustomer extends Order {
    private ArrayList<GoodInCart> goodsDetails;
    private long discountAmount;
    private String address;
    private String phoneNumber;
    private String postCode;
    private String name;

    public OrderForCustomer(ArrayList<GoodInCart> goodsDetails, long price, String name, String postCode, String address, String phoneNumber) {
        super(price);
        this.goodsDetails = goodsDetails;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
        this.name = name;
        this.discountAmount = MainController.getInstance().getAccountAreaForCustomerController().getTotalPriceOfCart() - price;
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
                "\nPost code : " + this.postCode +
                "\nAddress : " + this.getAddress() +
                "\nPhoneNumber : " + this.getPhoneNumber() +
                "\nOrder status : " + this.getOrderStatus() +
                "--------------------------------------------------------------------------------";

        return customerLog;
    }
}