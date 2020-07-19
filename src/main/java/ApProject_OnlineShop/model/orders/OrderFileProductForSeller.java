package ApProject_OnlineShop.model.orders;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.FileProduct;

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
}
