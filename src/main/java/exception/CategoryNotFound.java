package exception;

public class CategoryNotFound extends Exception {
    public CategoryNotFound() {
        super("this category not found");
    }
}
