package exception;

public class DiscountCodeCannotBeUsed extends Exception {
    public DiscountCodeCannotBeUsed() {
        super("this discount code does not support your purchase");
    }
}
