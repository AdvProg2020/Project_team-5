package exception;

public class PropertyOfCategoryNotFound extends Exception {
    public PropertyOfCategoryNotFound() {
        super("property with this name in category doesnt exist.");
    }
}
