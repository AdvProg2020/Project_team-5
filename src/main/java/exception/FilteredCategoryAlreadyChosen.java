package exception;

public class FilteredCategoryAlreadyChosen extends Exception {
    public FilteredCategoryAlreadyChosen() {
        super("category filter also enable\ndisable this category and then try again");
    }
}
