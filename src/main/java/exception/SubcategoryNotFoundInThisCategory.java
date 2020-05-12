package exception;

public class SubcategoryNotFoundInThisCategory extends Exception {
    public SubcategoryNotFoundInThisCategory() {
        super("subcategory with this name not exist in this category");
    }
}
