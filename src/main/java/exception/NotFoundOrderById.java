package exception;

public class NotFoundOrderById extends Exception {
    public NotFoundOrderById() {
        super("order by this ID does not exist");
    }
}
