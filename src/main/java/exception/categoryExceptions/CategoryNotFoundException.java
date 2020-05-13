package exception.categoryExceptions;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("category with this name doesnt exist");
    }
}
