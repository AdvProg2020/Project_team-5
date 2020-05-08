package exception;

public class DontHaveEnoughNumberOfThisProduct extends Exception {
    public DontHaveEnoughNumberOfThisProduct() {
        super("this seller doesn't have this number of this product");
    }
}
