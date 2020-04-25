package exceptions;

public class NotHaveThisProduct extends Exception {
    public NotHaveThisProduct() {
        super("You do not have product with this productId");
    }
}
