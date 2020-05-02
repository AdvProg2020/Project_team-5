package model.persons;

import model.orders.OrderForCustomer;
import model.productThings.DiscountCode;
import model.productThings.GoodInCart;

import java.util.ArrayList;

public class Customer extends Person {
    private ArrayList<DiscountCode> discountCodes;
    private ArrayList<OrderForCustomer> previousOrders;
    private long credit;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
    }

    public void addDiscountCode(DiscountCode discountCode) {
        this.discountCodes.add(discountCode);
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        this.discountCodes.remove(discountCode);
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public ArrayList<OrderForCustomer> getPreviousOrders() {
        return previousOrders;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public OrderForCustomer findOrderById(long orderId){
        for (OrderForCustomer order : previousOrders) {
            if (order.getOrderId() == orderId)
                return order;
        }
        return null;
    }

    public boolean hasBuyProduct(long productId){
        for (OrderForCustomer order : previousOrders) {
            for (GoodInCart goodInCart : order.getGoodsDetails()) {
                if (goodInCart.getGood().getGoodId() == productId)
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\ncredit = " + getCredit() + "\n" + "-------------------";
    }
}
