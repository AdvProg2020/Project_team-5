package view.productsPage;

import controller.MainController;
import exception.userExceptions.NotValidInput;
import view.LoginRegisterMenu;
import view.Menu;

import java.util.regex.Pattern;

public class FilteringMenu extends Menu {

    public FilteringMenu(Menu parentMenu) {
        super("filtering products Menu", parentMenu);
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("show available filters");
        commandNames.add("filter an available filter");
        commandNames.add("current filters");
        commandNames.add("disable filter");
    }

    @Override
    public void execute() {
        help();
        int chosenCommand = getInput();
        Menu nextMenu = this;
        if (chosenCommand==1)
            nextMenu = submenus.get(0);
        if (chosenCommand == 2)
            showAvailableFilters();
        if (chosenCommand == 3)
            filterAnAvailableFilter();
        if (chosenCommand == 4)
            currentFilters();
        if (chosenCommand == 5)
            disableFilters();
        if (chosenCommand == 6) {
            nextMenu = this.parentMenu;
        }
        nextMenu.execute();
    }

    private void showAvailableFilters() {
        System.out.println("1-category \n2-subcategory \n3-product name \n4-brand \n5-price \n6-category properties");
    }

    private void filterAnAvailableFilter() {
        System.out.println("enter an  available filter: ");
        showAvailableFilters();
        String chosenFilter = scanner.nextLine().trim();
        if (Pattern.matches("^[1-6]$", chosenFilter)) {
            executeFilterAnAvailableFilter(Integer.parseInt(chosenFilter));
        } else {
            System.out.println("not valid input");
        }
    }

    private void executeFilterAnAvailableFilter(int validFilter) {
        try {
            if (validFilter == 1)
                MainController.getInstance().getControllerForFiltering().addCategoryFilter(scanner.nextLine());
            if (validFilter == 2)
                MainController.getInstance().getControllerForFiltering().addSubCategoryFilter(scanner.nextLine());
            if (validFilter == 3)
                MainController.getInstance().getControllerForFiltering().addNameFiltering(scanner.nextLine());
            if (validFilter == 4)
                MainController.getInstance().getControllerForFiltering().addBrandFiltering(scanner.nextLine());
            if (validFilter == 5)
                getPriceFilter();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getPriceFilter() {
        System.out.println("enter minimum price:");
        String startValue = getValidInput("[\\d]+", "not valid amount");
        System.out.println("enter maximum price:");
        String endValue = getValidInput("[\\d]+", "not valid amount");
        MainController.getInstance().getControllerForFiltering().addPriceFiltering(startValue, endValue);
    }

    private void currentFilters() {
        int i = 1;
        for (String currentFilter : MainController.getInstance().getControllerForFiltering().getCurrentFilters()) {
            System.out.println("" + (i++) + "-" + currentFilter);
        }
    }

    private void disableFilters() {
        currentFilters();
        System.out.println("enter a current filter to disable the filter");
        String chosenFilter = scanner.nextLine().trim();
        try {
            if (!Pattern.matches("^[\\d]$", chosenFilter))
                throw new NotValidInput();
            if (Integer.parseInt(chosenFilter) > MainController.getInstance().getControllerForFiltering().getCurrentFilters().size())
                throw new NotValidInput();
            MainController.getInstance().getControllerForFiltering().disableFilter(Integer.parseInt(chosenFilter));
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }
}
