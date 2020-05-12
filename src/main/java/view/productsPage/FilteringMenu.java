package view.productsPage;

import controller.MainController;
import controller.sortingAndFiltering.ControllerForFiltering;
import view.Menu;
import view.OffsPage;

import java.util.regex.Pattern;

public class FilteringMenu extends Menu {

    public FilteringMenu(Menu parentMenu) {
        super("filtering products Menu", parentMenu);
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
        setController();
        int chosenCommand = getInput();
        Menu nextMenu = this;
        if (chosenCommand == 1)
            showAvailableFilters();
        if (chosenCommand == 2)
            filterAnAvailableFilter();
        if (chosenCommand == 3)
            currentFilters();
        if (chosenCommand == 4)
            disableFilters();
        if (chosenCommand == 5) {
            MainController.getInstance().getControllerForFiltering().resetAll();
            nextMenu = this.parentMenu;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void setController() {
        if (parentMenu instanceof AllProductsPage)
            MainController.getInstance().getControllerForFiltering().setGoodList(true);
        if (parentMenu instanceof OffsPage)
            MainController.getInstance().getControllerForFiltering().setGoodList(false);
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

    private void executeFilterAnAvailableFilter(int validFilter){
        try {
            if (validFilter == 1)
                MainController.getInstance().getControllerForFiltering().addCategoryFilter(scanner.nextLine());
            if (validFilter == 2)
                MainController.getInstance().getControllerForFiltering().addSubCategoryFilter(scanner.nextLine());
            if (validFilter == 3)
                //ToDo
            if (validFilter == 4)
                MainController.getInstance().getControllerForFiltering().addBrandFiltering(scanner.nextLine());
            if (validFilter == 5)
                getPriceFilter();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getPriceFilter(){
        System.out.println("enter minimum price:");
        String startValue =  getValidInput("[\\d]+", "not valid amount");
        System.out.println("enter maximum price:");
        String  endValue = getValidInput("[\\d]+", "not valid amount");
        MainController.getInstance().getControllerForFiltering().addPriceFiltering(startValue, endValue);
    }

    private void currentFilters() {
        for (String currentFilter : MainController.getInstance().getControllerForFiltering().getCurrentFilters()) {
            System.out.println(currentFilter);
        }
    }

    private void disableFilters() {

    }
}
