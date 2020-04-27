package exception;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException() {
        super("username not found!");
    }
}
