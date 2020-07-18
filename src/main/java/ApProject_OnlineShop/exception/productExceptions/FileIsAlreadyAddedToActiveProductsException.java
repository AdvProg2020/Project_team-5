package ApProject_OnlineShop.exception.productExceptions;

public class FileIsAlreadyAddedToActiveProductsException extends Exception {
    public FileIsAlreadyAddedToActiveProductsException() {
        super("this file is already added to your active products.");
    }
}
