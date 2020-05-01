package view.productsPage;

import view.Menu;

public class AllProductsPage extends Menu {
    public AllProductsPage(Menu parentMenu) {
        super("All products Menu", parentMenu);
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        submenus.add(new ProductPage(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("view categories");
        commandNames.add("show products");
    }

    private void showProducts() {
    }

    private void showCategories() {
    }

    @Override
    public void execute() {
        Menu nextMenu = null;
        int input = getInput();
        if (input == 1 || input == 2 || input == 3) {
            nextMenu = submenus.get(input - 1);
        } else if (input == 4) {
            showCategories();
            nextMenu = this;
        } else if (input == 5) {
            showProducts();
            nextMenu = this;
        } else if (input == 6) {
            nextMenu = this.getParentMenu();
        }
        nextMenu.help();
        nextMenu.execute();
    }
}
