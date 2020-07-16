package ApProject_OnlineShop.exception.productExceptions;

public class ProductIsAlreadyInAuctionException extends Exception{
    public ProductIsAlreadyInAuctionException() {
        super("you already put this product in auction");
    }
}
