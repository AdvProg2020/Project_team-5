package exception.productExceptions;

public class ThisProductIsnotInAnyOff extends Exception {
    public ThisProductIsnotInAnyOff() {
        super("this product doesn't exist in any off!");
    }
}
