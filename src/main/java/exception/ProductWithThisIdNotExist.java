package exception;

public class ProductWithThisIdNotExist extends Exception {
    public ProductWithThisIdNotExist() {
        super("Product with this ID does not exist");
    }
}
