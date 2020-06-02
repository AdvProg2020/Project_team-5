package ApProject_OnlineShop.exception.productExceptions;

public class DontHaveEnoughNumberOfThisProduct extends Exception {
    public DontHaveEnoughNumberOfThisProduct() {
        super("this seller doesn't have this number of this product");
    }
}
