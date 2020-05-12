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

    private void setController(){
        if (parentMenu instanceof AllProductsPage)
            MainController.getInstance().getControllerForFiltering().setGoodList(true);
        if (parentMenu instanceof OffsPage)
            MainController.getInstance().getControllerForFiltering().setGoodList(false);
    }

    private void showAvailableFilters() {
        System.out.println("1-category \n2-subcategory \n3-product name \n4-brand \n5-price \n6-category properties");
    }

    private void filterAnAvailableFilter() {
        showAvailableFilters();
        String chosenFilter = scanner.nextLine().trim();
        if (Pattern.matches("^[1-6]$", chosenFilter)){
            int validFilter = Integer.parseInt(chosenFilter);
        }else {
            System.out.println("not valid input");
        }
    }

    private void currentFilters() {
        for (String currentFilter : MainController.getInstance().getControllerForFiltering().getCurrentFilters()) {
            System.out.println(currentFilter);
        }
    }

    private void disableFilters() {

    }
}
