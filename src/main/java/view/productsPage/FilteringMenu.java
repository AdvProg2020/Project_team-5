package view.productsPage;

import controller.sortingAndFiltering.ControllerForFiltering;
import view.Menu;

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
        if (chosenCommand == 5)
            nextMenu = this.parentMenu;
        nextMenu.help();
        nextMenu.execute();
    }

    private void showAvailableFilters() {
        System.out.println("1-category and subcategory \n2-product name \n3-brand \n4-price");
    }

    private void filterAnAvailableFilter() {

    }

    private void currentFilters() {
        
    }

    private void disableFilters() {

    }
}
