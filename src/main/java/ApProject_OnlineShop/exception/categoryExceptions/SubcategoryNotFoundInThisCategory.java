package ApProject_OnlineShop.exception.categoryExceptions;

public class SubcategoryNotFoundInThisCategory extends Exception {
    public SubcategoryNotFoundInThisCategory() {
        super("subcategory with this name not exist in this category");
    }
}
