package exception;

public class ProductNotFoundExceptionForSeller extends Exception {
    public ProductNotFoundExceptionForSeller() {
        super("You do not have product with this productId");
    }
}
