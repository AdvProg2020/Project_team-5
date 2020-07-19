package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.FileProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderFileProductForCustomer extends Order {
    private long discountAmount;
    private String phoneNumber;
    private Long fileProductId;

    public OrderFileProductForCustomer(FileProduct fileProduct, long price, String phoneNumber, long discountAmount) {
        super(price);
        this.fileProductId = fileProduct.getFileProductId();
        this.phoneNumber = phoneNumber;
        this.discountAmount = discountAmount;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public FileProduct getFileProduct() {
        return Shop.getInstance().findFileProductById(fileProductId);
    }

    public void setFileProductId(FileProduct fileProduct) {
        this.fileProductId = fileProduct.getFileProductId();
    }

    public List<String> getDetails() {
        ArrayList<String> orderDetails = new ArrayList<>();
        orderDetails.add("" + getOrderId());
        orderDetails.add(getDate().toString());
        String goods = "";
        goods += "name: " + getFileProduct().getName() + "\ndescription: " + getFileProduct().getDescription() + "\nseller: " + getFileProduct().getSeller().getUsername();
        orderDetails.add(goods);
        orderDetails.add(discountAmount + "");
        orderDetails.add("" + getPrice());
        orderDetails.add(getPhoneNumber());
        orderDetails.add(getOrderStatus().toString());
        return orderDetails;
    }
}
