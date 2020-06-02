package ApProject_OnlineShop.exception;

public class NotBuyProductByThisId extends Exception {
    public NotBuyProductByThisId() {
        super("you have not bought this product");
    }
}
