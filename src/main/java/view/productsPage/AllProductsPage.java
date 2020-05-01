package view.productsPage;

import controller.MainController;
import exception.ProductNotFoundException;
import view.Menu;
import view.ScreenClearing;

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
        //ToDo
    }

    private Menu showAProduct() {
        System.out.println("enter product id");
        long id = Long.parseLong(getValidInput("^(\\d{1}|\\d{14})$", "you must enter a number!"));
        try {
            MainController.getInstance().getAllProductsController().showAProduct(id);
            return this.submenus.get(2);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
            return this;
        }
    }

    private void showCategories() {
        ScreenClearing.clearScreen();
        System.out.println(MainController.getInstance().getAllProductsController().showCategories());
        System.out.println("press any key to continue");
        scanner.nextLine();
    }

    @Override
    public void execute() {
        Menu nextMenu = null;
        int input = getInput();
        if (input == 1 || input == 2) {
            nextMenu = submenus.get(input - 1);
        } else if (input == 3) {
            nextMenu = showAProduct();
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
