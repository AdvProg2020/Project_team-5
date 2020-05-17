package model.persons;

import model.Shop;
import model.orders.OrderForCustomer;
import model.productThings.DiscountCode;
import model.productThings.GoodInCart;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends Person {
    private ArrayList<Long> discountCodesIds;
    private ArrayList<OrderForCustomer> previousOrders;
    private long credit;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, long credit) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.credit = credit;
        this.discountCodesIds = new ArrayList<>();
        this.previousOrders = new ArrayList<>();
    }

    public void addDiscountCode(DiscountCode discountCode) {
        this.discountCodesIds.add(discountCode.getId());
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        this.discountCodesIds.remove(discountCode.getId());
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        ArrayList<DiscountCode> discountCodes=new ArrayList<>();
        for (Long discountCodesId : this.discountCodesIds) {
            discountCodes.add(Shop.getInstance().getHashMapOfDiscountCodes().get(discountCodesId));
        }
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

    public void addOrder(OrderForCustomer order) {
        previousOrders.add(order);
    }

    public OrderForCustomer findOrderById(long orderId) {
        for (OrderForCustomer order : previousOrders) {
            if (order.getOrderId() == orderId)
                return order;
        }
        return null;
    }

    public DiscountCode findDiscountCode(String code) {
        for (DiscountCode discountCode : this.getDiscountCodes()) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public boolean hasBuyProduct(long productId) {
        for (OrderForCustomer order : previousOrders) {
            for (GoodInCart goodInCart : order.getGoodsDetails()) {
                if (goodInCart.getGood().getGoodId() == productId)
                    return true;
            }
        }
        return false;
    }

    public void donateDiscountCodeTOBestCustomers() {
        long allPricesOfOrdersWithOutLastOne = 0l;
        for (OrderForCustomer order : this.getPreviousOrders()) {
            if (!order.equals(this.getPreviousOrders().get(this.getPreviousOrders().size() - 1))) {
                allPricesOfOrdersWithOutLastOne += order.getPrice();
            }
        }
        if (((allPricesOfOrdersWithOutLastOne + this.getPreviousOrders().get(this.getPreviousOrders().size() - 1).getPrice()) / 1000000)
                - (allPricesOfOrdersWithOutLastOne / 1000000) > 0){
            DiscountCode discountCode=new DiscountCode(DiscountCode.generateRandomDiscountCode()
                    , LocalDate.now(),LocalDate.now().plusMonths(1),10000l,30);
            discountCode.addCustomerToCode(this,1);
            Shop.getInstance().addDiscountCode(discountCode);
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "\ncredit = " + getCredit() + "\n" + "-------------------";
    }
}
