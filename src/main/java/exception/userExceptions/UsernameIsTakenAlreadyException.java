package exception.userExceptions;

public class UsernameIsTakenAlreadyException extends Exception {
    public UsernameIsTakenAlreadyException() {
        super("UserName is taken already");
    }
}
