package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.FileProduct;

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
}
