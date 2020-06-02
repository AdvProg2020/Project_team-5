package ApProject_OnlineShop.exception.userExceptions;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException() {
        super("username not found!");
    }
}
