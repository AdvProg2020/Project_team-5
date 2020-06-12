package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.GoodInCart;

import java.util.ArrayList;
import java.util.List;

public class OrderForCustomer extends Order {
    private ArrayList<Long> goodsDetails;
    private long discountAmount;
    private String address;
    private String phoneNumber;
    private String postCode;
    private String name;

    public OrderForCustomer(ArrayList<GoodInCart> goodsDetails, long price, String name, String postCode, String address, String phoneNumber) {
        super(price);
        this.goodsDetails = new ArrayList<>();
        for (GoodInCart goodInCart : goodsDetails) {
            this.goodsDetails.add(goodInCart.getGoodInCartId());
        }
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
        this.name = name;
        this.discountAmount = MainController.getInstance().getAccountAreaForCustomerController().
                finalPriceOfAList(Shop.getInstance().getCart()) - price;
    }

    public ArrayList<GoodInCart> getGoodsDetails() {
        ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        for (Long id : goodsDetails) {
            goodInCarts.add(Shop.getInstance().getAllGoodInCarts().get(id));
        }
        return goodInCarts;
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
                "\n--------------------------------------------------------------------------------";

        return customerLog;
    }

    public List<String> getDetails() {
        ArrayList<String> orderDetails = new ArrayList<>();
        orderDetails.add("" + getOrderId());
        orderDetails.add(getDate().toString());
        String goods = "";
        for (GoodInCart goodInCart : getGoodsDetails()) {
            goods += "- "+goodInCart.getBriefString();
        }
        orderDetails.add(goods);
        orderDetails.add("" + getDiscountAmount());
        orderDetails.add(postCode);
        orderDetails.add(getAddress());
        orderDetails.add(getPhoneNumber());
        orderDetails.add(getOrderStatus().toString());
        return orderDetails;
    }
}