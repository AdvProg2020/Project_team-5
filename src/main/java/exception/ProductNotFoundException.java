package exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException () {
        super("You do not have product with this productId");
    }
}
