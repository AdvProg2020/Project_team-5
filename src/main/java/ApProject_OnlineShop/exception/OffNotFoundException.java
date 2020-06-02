package ApProject_OnlineShop.exception;

public class OffNotFoundException extends Exception {
    public OffNotFoundException() {
        super("Off not found with this Id");
    }
}
