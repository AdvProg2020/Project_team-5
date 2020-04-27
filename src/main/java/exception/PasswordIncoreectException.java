package exception;

public class PasswordIncoreectException extends Exception {
    public PasswordIncoreectException() {
        super("password is incorrect!");
    }
}
