package ApProject_OnlineShop.exception.userExceptions;

public class MainManagerAlreadyRegistered extends Exception{
    public MainManagerAlreadyRegistered() {
        super("you can not create manager account.only main manager can create manager account");
    }
}
