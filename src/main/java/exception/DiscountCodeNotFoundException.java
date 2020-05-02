package exception;

public class DiscountCodeNotFoundException extends Exception {
    public DiscountCodeNotFoundException() {
        super("discount code not found.");
    }
}
