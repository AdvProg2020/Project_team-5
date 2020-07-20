package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.productThings.GoodInCart;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OrderForCustomer")
public class OrderForCustomer extends Order implements Serializable {
    @ManyToMany
    @JoinTable(
            name = "OrderAndGoodInCart",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "GoodInCartId"))
    private ArrayList<GoodInCart> goodsDetails;

    @ManyToOne
    @Column(name = "CustomerID", nullable = false)
    private Customer customer;

    @Column(name = "DiscountAmount", nullable = false)
    private long discountAmount;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "PostalCode", nullable = false)
    private String postCode;

    @Column(name = "Name")
    private String name;

    public OrderForCustomer(ArrayList<GoodInCart> goodsDetails, long price, String name, String postCode, String address, String phoneNumber,long id) {
        super(price);
        this.goodsDetails = goodsDetails;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
        this.name = name;
        if (id != -1) {
            this.discountAmount = MainController.getInstance().getAccountAreaForCustomerController().
                    finalPriceOfAList(Shop.getInstance().getCart(id)) - price;
        } else this.discountAmount = 0L;
    }

    public OrderForCustomer() {
        this.goodsDetails = new ArrayList<>();
    }

    public ArrayList<GoodInCart> getGoodsDetails() {
        return this.goodsDetails;
        /*ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        for (Long id : goodsDetails) {
            goodInCarts.add(Shop.getInstance().getAllGoodInCarts().get(id));
        }
        return goodInCarts;*/
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public String getPostCode() {
        return postCode;
    }

    public String getName() {
        return name;
    }

    public void setGoodsDetails(ArrayList<GoodInCart> goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setName(String name) {
        this.name = name;
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
            goods += "- " + goodInCart.getBriefString() + "\n";
        }
        orderDetails.add(goods);
        orderDetails.add("" + getDiscountAmount());
        orderDetails.add("" + getPrice());
        orderDetails.add(postCode);
        orderDetails.add(getAddress());
        orderDetails.add(getPhoneNumber());
        orderDetails.add(getOrderStatus().toString());
        return orderDetails;
    }
}