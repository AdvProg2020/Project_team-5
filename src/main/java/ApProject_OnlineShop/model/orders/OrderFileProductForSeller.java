package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.FileProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderFileProductForSeller extends Order {
    private String seller;
    private String customerName;
    private long fileProduct;

    public OrderFileProductForSeller(long price, Seller seller, String customerName, FileProduct fileProduct) {
        super(price);
        this.seller = seller.getUsername();
        this.customerName = customerName;
        this.fileProduct = fileProduct.getFileProductId();
    }

    public Seller getSeller() {
        return (Seller) Shop.getInstance().findUser(this.seller);
    }

    public void setSeller(Seller seller) {
        this.seller = seller.getUsername();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public FileProduct getFileProduct() {
        return Shop.getInstance().findFileProductById(fileProduct);
    }

    public void setFileProduct(FileProduct fileProduct) {
        this.fileProduct = fileProduct.getFileProductId();
    }

    public List<String> getDetails() {
        ArrayList<String> orderDetails = new ArrayList<>();
        orderDetails.add("" + getOrderId());
        orderDetails.add(getDate().toString());
        String goods = "";
        goods += "name: " + getFileProduct().getName() + "\ndescription: " + getFileProduct().getDescription() + "\nseller: " + getFileProduct().getSeller().getUsername();
        orderDetails.add(goods);
        orderDetails.add("" + getPrice());
        orderDetails.add(getOrderStatus().toString());
        return orderDetails;
    }
}
