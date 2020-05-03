package exception;

public class DiscountCodeCantCreatedException extends Exception{
    public DiscountCodeCantCreatedException(String incorrectField) {
        super("can not create discount code because " + incorrectField + " is incorrect.");
    }
}
