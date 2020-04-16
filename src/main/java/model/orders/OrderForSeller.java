package model.orders;

import model.persons.Seller;
import model.productThings.Good;
import model.productThings.GoodInCart;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderForSeller extends Order {
    private Seller seller;
    private String customerName;
    private long offDeduct;
    private HashMap<Good, Integer> numberPerGood = new HashMap<>();

    public OrderForSeller(Seller seller, String customerName, ArrayList<GoodInCart> goods) {
        this.seller = seller;
        this.customerName = customerName;
        setNumberPerGood(goods);
    }

    private void setNumberPerGood(ArrayList<GoodInCart> goods) {
        for (GoodInCart good : goods) {
            numberPerGood.put(good.getGood(), good.getNumber());
        }
    }

    public Seller getSeller() {
        return seller;
    }

    public String getCustomerName() {
        return customerName;
    }

    public long getOffDeduct() {
        return offDeduct;
    }

    public HashMap<Good, Integer> getNumberPerGood() {
        return numberPerGood;
    }



    @Override
    public String toString() {
        String sellerLog = "--------------------------------------------------------------------------------" +
                "\nOrderId : " + this.getOrderId() +
                "\nDate : " + this.getDate() +
                "\nGoodsList :";
        for (Good good : this.numberPerGood.keySet()) {
            sellerLog += "\nname : " + good.getName() + "\tbrand : " + good.getBrand() + "\tprice before off : " + good.getPriceBySeller(getSeller())
                    // + "price after off : " + getGood.getPriceAfterOff
                    + "\tnumber :" + numberPerGood.get(good);
        }
        sellerLog += "\nPaid price : " + this.getPrice() +
                "\nDiscount amount : " + this.getOffDeduct() +
                "\nOrder status : " + this.getOrderStatus() +
                "--------------------------------------------------------------------------------";
        return sellerLog;
    }
}