package ApProject_OnlineShop.exception.categoryExceptions;

public class SubCategoryNotFoundException extends Exception {
    public SubCategoryNotFoundException() {
        super("subcategory not found with this name.");
    }
}
