package ApProject_OnlineShop.view;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.exception.productExceptions.ThisProductIsnotInAnyOff;
import ApProject_OnlineShop.view.productsPage.FilteringMenu;
import ApProject_OnlineShop.view.productsPage.ProductPage;
import ApProject_OnlineShop.view.productsPage.SortingMenu;

public class OffsPage extends Menu {

    public OffsPage(Menu parentMenu) {
        super("Offs menu", parentMenu);
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        submenus.add(new ProductPage(this));
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("show off products");
    }

    private Menu showAProduct() {
        System.out.println("enter product id");
        long id = Long.parseLong(getValidInput("^(\\d{1}|\\d{14})$", "you must enter a number!"));
        try {
            MainController.getInstance().getOffsController().showAProduct(id);
            return this.submenus.get(2);
        } catch (ProductWithThisIdNotExist e) {
            System.out.println(e.getMessage());
            return this;
        } catch (ThisProductIsnotInAnyOff e) {
            System.out.println(e.getMessage());
            return this;
        }
    }

    private void showOffProducts() {
        System.out.println(MainController.getInstance().getOffsController().showOffProducts());
    }

    @Override
    public void execute() {
        help();
        MainController.getInstance().getControllerForFiltering().setGoodList(false);
        Menu nextMenu = null;
        int input = getInput();
        if (input == 1 || input == 2 || input == 4) {
            nextMenu = submenus.get(input - 1);
        } else if (input == 3) {
            nextMenu = showAProduct();
        } else if (input == 5) {
            showOffProducts();
            nextMenu = this;
        } else if (input == 6) {
            MainController.getInstance().getControllerForFiltering().resetAll();
            nextMenu = this.getParentMenu();
        }
        nextMenu.execute();
    }
}
