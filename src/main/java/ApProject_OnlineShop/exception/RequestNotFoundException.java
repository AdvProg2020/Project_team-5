package ApProject_OnlineShop.exception;

public class RequestNotFoundException extends Exception {
    public RequestNotFoundException() {
        super("request with this id doesnt exist.");
    }
}
