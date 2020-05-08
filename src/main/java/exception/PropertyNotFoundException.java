package exception;

public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException() {
        super("property with this name in category/subcategory doesnt exist.");
    }
}
