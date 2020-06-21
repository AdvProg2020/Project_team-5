package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.GoodInCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderForSeller extends Order {
    private String seller;
    private String customerName;
    private long offDeduct;
    private HashMap<Long, Integer> numberPerGood;

    public OrderForSeller(long price, Seller seller, String customerName, List<GoodInCart> goods) {
        super(price);
        this.seller = seller.getUsername();
        this.customerName = customerName;
        setNumberPerGood(goods);
        calculateDeductAmount();
    }

    private void setNumberPerGood(List<GoodInCart> goods) {
        numberPerGood=new HashMap<>();
        if (goods == null) return;
        for (GoodInCart good : goods) {
            numberPerGood.put(good.getGood().getGoodId(), good.getNumber());
        }
    }

    private void calculateDeductAmount() {
        int primaryPrice = 0;
        for (Good good : getNumberPerGood().keySet()) {
            primaryPrice += getNumberPerGood().get(good) * good.getPriceBySeller((Seller) Shop.getInstance().findUser(seller));
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
        HashMap<Good, Integer> numberPerGood1 = new HashMap<>();
        for (Long id : this.numberPerGood.keySet()) {
            numberPerGood1.put(Shop.getInstance().findGoodById(id), this.numberPerGood.get(id));
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

    public List<String> getDetails() {
        ArrayList<String> orderDetails = new ArrayList<>();
        orderDetails.add("" + getOrderId());
        orderDetails.add(getDate().toString());
        String goods = "";
        for (Long id : numberPerGood.keySet()) {
            goods += "- " + "name: " + Shop.getInstance().findGoodById(id).getName() + "   \t brand: " + Shop.getInstance().findGoodById(id).getBrand() + "   \t number: " + numberPerGood.get(id);
        }
        orderDetails.add(goods);
        orderDetails.add("" + getOffDeduct());
        orderDetails.add(getOrderStatus().toString());
        return orderDetails;
    }
}