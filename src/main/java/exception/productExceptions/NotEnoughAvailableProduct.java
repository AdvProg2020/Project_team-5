package exception.productExceptions;

public class NotEnoughAvailableProduct extends Exception {
    public NotEnoughAvailableProduct() {
        super("Not enough Available product");
    }
}
