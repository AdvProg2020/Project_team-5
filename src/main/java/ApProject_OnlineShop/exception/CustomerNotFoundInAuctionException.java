package ApProject_OnlineShop.exception;

public class CustomerNotFoundInAuctionException extends Exception {
    public CustomerNotFoundInAuctionException() {
        super("you are not in this auction");
    }
}
