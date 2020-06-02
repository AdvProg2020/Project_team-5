package ApProject_OnlineShop.exception.discountcodeExceptions;

public class DiscountCodeNotFoundException extends Exception {
    public DiscountCodeNotFoundException() {
        super("discount code not found.");
    }
}
