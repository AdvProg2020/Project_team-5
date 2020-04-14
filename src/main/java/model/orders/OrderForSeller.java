package model.orders;

import model.persons.Seller;
import model.productThings.Good;
import model.productThings.GoodInCart;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderForSeller extends Order{
    private Seller seller;
    private String customerName;
    private double offDeduct;
    private HashMap<Good,Integer> numberPerGood = new HashMap<Good, Integer>();

    public OrderForSeller(Seller seller, String customerName, ArrayList<GoodInCart> goods) {
        this.seller = seller;
        this.customerName = customerName;
        setNumberPerGood(goods);
    }

    private void setNumberPerGood(ArrayList<GoodInCart> goods){
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

    public double getOffDeduct() {
        return offDeduct;
    }

    public HashMap<Good, Integer> getNumberPerGood() {
        return numberPerGood;
    }
}