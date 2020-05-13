package exception.categoryExceptions;

public class HaveNotChosenCategoryFilter extends Exception {
    public HaveNotChosenCategoryFilter() {
        super("you have not chosen category filter");
    }
}
