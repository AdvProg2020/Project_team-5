package exception;

public class ProductNotExistInCart extends Exception{
    public ProductNotExistInCart() {
        super("not valid in cart product ID");
    }
}
