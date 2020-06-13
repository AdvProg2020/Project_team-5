package ApProject_OnlineShop.exception.productExceptions;

public class YouRatedThisProductBefore extends Exception {
    public YouRatedThisProductBefore() {
        super("you rated this good before!");
    }
}
