package ApProject_OnlineShop.exception.discountcodeExceptions;

public class DiscountCodeExpired extends Exception {
    public DiscountCodeExpired() {
        super("this discount code has expired");
    }
}
