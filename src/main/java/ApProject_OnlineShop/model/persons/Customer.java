package ApProject_OnlineShop.model.persons;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.productThings.GoodInCart;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "Customer")
public class Customer extends Person implements Serializable {

    @ManyToMany
    @JoinTable(name = "DiscountPerson", joinColumns = @JoinColumn(name = "CustomerId"), inverseJoinColumns = @JoinColumn(name = "DiscountId"))
    private ArrayList<DiscountCode> discountCodes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private ArrayList<OrderForCustomer> previousOrders;

    @Column(name = "BankAccountId")
    private String bankAccountId;

    @Column(name = "Credit", nullable = false)
    private long credit;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, long credit) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.credit = credit;
        this.discountCodes = new ArrayList<>();
        this.previousOrders = new ArrayList<>();
    }

    public Customer() {
        this.discountCodes = new ArrayList<>();
        this.previousOrders = new ArrayList<>();
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void addDiscountCode(DiscountCode discountCode) {
        this.discountCodes.add(discountCode);
        //this.discountCodes.add(discountCode.getId());
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        this.discountCodes.remove(discountCode);
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return this.discountCodes;
        /*ArrayList<DiscountCode> discountCodes=new ArrayList<>();
        for (Long discountCodesId : this.discountCodes) {
            discountCodes.add(Shop.getInstance().getHashMapOfDiscountCodes().get(discountCodesId));
        }
        return discountCodes;*/
    }

    public ArrayList<OrderForCustomer> getPreviousOrders() {
        return this.previousOrders;
        /*ArrayList<OrderForCustomer> previousOrders=new ArrayList<>();
        for (Long id : this.previousOrders) {
            previousOrders.add((OrderForCustomer) Shop.getInstance().getHasMapOfOrders().get(id));
        }
        return previousOrders;*/
    }

    public void setDiscountCodes(ArrayList<DiscountCode> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public void setPreviousOrders(ArrayList<OrderForCustomer> previousOrders) {
        this.previousOrders = previousOrders;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void addOrder(OrderForCustomer order) {
        previousOrders.add(order);
        //previousOrders.add(order.getOrderId());
    }

    public OrderForCustomer findOrderById(long orderId) {
        for (OrderForCustomer order : this.getPreviousOrders()) {
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
        for (OrderForCustomer order : this.getPreviousOrders()) {
            for (GoodInCart goodInCart : order.getGoodsDetails()) {
                if (goodInCart.getGood().getGoodId() == productId)
                    return true;
            }
        }
        return false;
    }

    public void donateDiscountCodeTOBestCustomers() throws IOException, FileCantBeSavedException {
        long allPricesOfOrdersWithOutLastOne = 0L;
        for (OrderForCustomer order : this.getPreviousOrders()) {
            if (!order.equals(this.getPreviousOrders().get(this.getPreviousOrders().size() - 1))) {
                allPricesOfOrdersWithOutLastOne += order.getPrice();
            }
        }
        if (((allPricesOfOrdersWithOutLastOne + this.getPreviousOrders().get(this.getPreviousOrders().size() - 1).getPrice()) / 1000000)
                - (allPricesOfOrdersWithOutLastOne / 1000000) > 0){
            DiscountCode discountCode=new DiscountCode(DiscountCode.generateRandomDiscountCode()
                    , LocalDateTime.now(),LocalDateTime.now().plusMonths(1), 10000L,30);
            discountCode.addCustomerToCode(this,1);
            Shop.getInstance().addDiscountCode(discountCode);
            Database.getInstance().saveItem(discountCode);
            Database.getInstance().saveItem(this);
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "\ncredit = " + getCredit() + "\n" + "-------------------";
    }
}
