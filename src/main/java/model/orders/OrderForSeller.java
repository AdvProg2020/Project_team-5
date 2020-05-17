package model.orders;

import model.Shop;
import model.persons.Seller;
import model.productThings.Good;
import model.productThings.GoodInCart;

import java.util.HashMap;
import java.util.List;

public class OrderForSeller extends Order {
    private String seller;
    private String customerName;
    private long offDeduct;
    private HashMap<Long, Integer> numberPerGood = new HashMap<>();

    public OrderForSeller(long price, Seller seller, String customerName, List<GoodInCart> goods) {
        super(price);
        this.seller = seller.getUsername();
        this.customerName = customerName;
        calculateDeductAmount();
        setNumberPerGood(goods);
    }

    private void setNumberPerGood(List<GoodInCart> goods) {
        for (GoodInCart good : goods) {
            numberPerGood.put(good.getGood().getGoodId(), good.getNumber());
        }
    }

    private void calculateDeductAmount() {
        int primaryPrice = 0;
        for (Good good : getNumberPerGood().keySet()) {
            primaryPrice += getPrice() * good.getPriceBySeller((Seller) Shop.getInstance().findUser(seller));
        }
        offDeduct = primaryPrice - getPrice();
    }

    public Seller getSeller() {
        return (Seller) Shop.getInstance().findUser(seller);
    }

    public String getCustomerName() {
        return customerName;
    }

    public long getOffDeduct() {
        return offDeduct;
    }

    public HashMap<Good, Integer> getNumberPerGood() {
        HashMap<Good,Integer> numberPerGood1=new HashMap<>();
        for (Long id : this.numberPerGood.keySet()) {
            numberPerGood1.put(Shop.getInstance().findGoodById(id),this.numberPerGood.get(id));
        }
        return numberPerGood1;
    }

    @Override
    public String toString() {
        String sellerLog = "--------------------------------------------------------------------------------" +
                "\nOrderId : " + this.getOrderId() +
                "\nDate : " + this.getDate() +
                "\nGoodsList :";
        for (Good good : this.getNumberPerGood().keySet()) {
            sellerLog += ("\nname : " + good.getName() + "\tbrand : " + good.getBrand());
            if (Shop.getInstance().getFinalPriceOfAGood(good, getSeller()) != good.getPriceBySeller(getSeller()))
                sellerLog += ("\tprice before off : " + good.getPriceBySeller(getSeller())
                        + "price after off : " + Shop.getInstance().getFinalPriceOfAGood(good, getSeller()));
            else
                sellerLog += ("\tprice : " + good.getPriceBySeller(getSeller()));
            sellerLog += ("\tnumber :" + numberPerGood.get(good));
        }
        sellerLog += ("\nPaid price : " + this.getPrice() +
                "\nDiscount amount : " + this.getOffDeduct() +
                "\nOrder status : " + this.getOrderStatus() +
                "\n--------------------------------------------------------------------------------");
        return sellerLog;
    }
}