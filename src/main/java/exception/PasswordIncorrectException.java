package exception;

public class PasswordIncorrectException extends Exception {
    public PasswordIncorrectException() {
        super("password is incorrect!");
    }
}
