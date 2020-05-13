package exception.productExceptions;

public class ProductNotFoundExceptionForSeller extends Exception {
    public ProductNotFoundExceptionForSeller() {
        super("You do not have product with this productId");
    }
}
