package view;

import controller.MainController;
import exception.ProductWithThisIdNotExist;
import model.Shop;
import view.productsPage.FilteringMenu;
import view.productsPage.ProductPage;
import view.productsPage.SortingMenu;

public class OffsPage extends Menu{

    public OffsPage(Menu parentMenu) {
        super("Offs menu", parentMenu);
        submenus.add(new FilteringMenu(this));
        submenus.add(new SortingMenu(this));
        submenus.add(new ProductPage(this));
    }

    @Override
    protected void setCommandNames() {

    }

    private Menu showAProduct() {
        System.out.println("enter product id");
        long id = Long.parseLong(getValidInput("^(\\d{1}|\\d{14})$", "you must enter a number!"));
        try {
            MainController.getInstance().getAllProductsController().showAProduct(id);
            return this.submenus.get(2);
        } catch (ProductWithThisIdNotExist e) {
            System.out.println(e.getMessage());
            return this;
        }
    }

    private String showOffProducts(){
        return MainController.getInstance().getOffsController().showOffProducts();
    }

    @Override
    public void execute() {

        System.out.println("--------------------------------------");
        System.out.println("off products :");
        System.out.println(showOffProducts());
        Menu nextMenu = null;
        int input = getInput();
        if (input == 1 || input == 2) {
            nextMenu = submenus.get(input - 1);
        } else if (input == 3) {
            nextMenu = showAProduct();
        } else if (input == 4) {
            nextMenu = this.getParentMenu();
        }
        nextMenu.help();
        nextMenu.execute();
    }
}
