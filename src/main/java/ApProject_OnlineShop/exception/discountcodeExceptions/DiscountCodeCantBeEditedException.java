package ApProject_OnlineShop.exception.discountcodeExceptions;

public class DiscountCodeCantBeEditedException extends Exception {
    public DiscountCodeCantBeEditedException(String error) {
        super("can not edit discount code because " + error + " is incorrect.");
    }
}
