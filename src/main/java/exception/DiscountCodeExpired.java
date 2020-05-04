package exception;

public class DiscountCodeExpired extends Exception {
    public DiscountCodeExpired() {
        super("this discount code has expired");
    }
}
