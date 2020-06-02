package ApProject_OnlineShop.view.productsPage;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.view.LoginRegisterMenu;
import ApProject_OnlineShop.view.Menu;
import ApProject_OnlineShop.view.ScreenClearing;

public class AllProductsPage extends Menu {
    public AllProductsPage(Menu parentMenu) {
        super("All products Menu", parentMenu);
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        submenus.add(new ProductPage(this));
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("ApProject_OnlineShop.view categories");
        commandNames.add("show products");
    }

    private void showProducts() {
        System.out.println(MainController.getInstance().getAllProductsController().showProducts());
    }

    private Menu showAProduct() {
        System.out.println("enter product id");
        long id = Long.parseLong(getValidInput("^([\\d]{1,14})$", "you must enter a number!"));
        try {
            MainController.getInstance().getAllProductsController().showAProduct(id);
            return this.submenus.get(2);
        } catch (ProductWithThisIdNotExist e) {
            System.out.println(e.getMessage());
            System.out.println("press enter to continue");
            scanner.nextLine();
            return this;
        }
    }

    private void showCategories() {
        ScreenClearing.clearScreen();
        System.out.print(MainController.getInstance().getAllProductsController().showCategories());
        System.out.println("press any key to continue");
        scanner.nextLine();
    }

    @Override
    public void execute() {
        help();
        MainController.getInstance().getControllerForFiltering().setGoodList(true);
        Menu nextMenu = null;
        int input = getInput();
        if (input == 1 || input == 2 || input == 4) {
            nextMenu = submenus.get(input - 1);
        } else if (input == 3) {
            nextMenu = showAProduct();
        } else if (input == 5) {
            showCategories();
            nextMenu = this;
        } else if (input == 6) {
            showProducts();
            nextMenu = this;
        } else if (input == 7) {
            MainController.getInstance().getControllerForFiltering().resetAll();
            nextMenu = this.getParentMenu();
        }
        nextMenu.execute();
    }
}
