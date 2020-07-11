package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.GoodInCart;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "OrderForSeller")
public class OrderForSeller extends Order implements Serializable {
    @ManyToOne
    @JoinColumn(name = "SellerId", referencedColumnName = "PersonId", nullable = false)
    private Seller seller;

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "OffDeduct", nullable = false)
    private long offDeduct;

    @ManyToMany
    @JoinTable(
            name = "OrderAndGoodInCart",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "GoodInCartId"))
    private List<GoodInCart> goods;

    public OrderForSeller(long price, Seller seller, String customerName, List<GoodInCart> goods) {
        super(price);
        this.seller = seller;
        this.customerName = customerName;
        this.goods = goods;
        calculateDeductAmount();
    }

    public OrderForSeller() {
        this.goods = new ArrayList<>();
    }

    /*private void setNumberPerGood(List<GoodInCart> goods) {
        numberPerGood=new HashMap<>();
        if (goods == null) return;
        for (GoodInCart good : goods) {
            numberPerGood.put(good.getGood(), good.getNumber());
        }
    }*/

    private void calculateDeductAmount() {
        int primaryPrice = 0;
        for (Good good : getNumberPerGood().keySet()) {
            primaryPrice += getNumberPerGood().get(good) * good.getPriceBySeller(seller);
        }
        offDeduct = primaryPrice - getPrice();
    }

    public Seller getSeller() {
        return this.seller;
    }

    public String getCustomerName() {
        return customerName;
    }

    public long getOffDeduct() {
        return offDeduct;
    }

    public List<GoodInCart> getGoods() {
        return goods;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOffDeduct(long offDeduct) {
        this.offDeduct = offDeduct;
    }

    public void setGoods(List<GoodInCart> goods) {
        this.goods = goods;
    }

    public HashMap<Good, Integer> getNumberPerGood() {
        HashMap<Good, Integer> numberPerGood1 = new HashMap<>();
        for (GoodInCart goodInCart : this.goods) {
            numberPerGood1.put(goodInCart.getGood(), goodInCart.getNumber());
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
            sellerLog += ("\tnumber :" + this.getNumberPerGood().get(good));
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
        for (GoodInCart goodInCart : this.goods) {
            goods += "- " + "name: " + goodInCart.getGood().getName() + "   \t brand: " + goodInCart.getGood().getBrand() + "   \t number: " + goodInCart.getNumber();
        }
        orderDetails.add(goods);
        orderDetails.add("" + getOffDeduct());
        orderDetails.add(getOrderStatus().toString());
        return orderDetails;
    }
}