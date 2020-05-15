package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.LoginRegisterMenu;
import view.Menu;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Menu parentMenu) {
        super("manage all products", parentMenu);
        this.submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("remove");
    }

    @Override
    public void execute() {
        help();
        int chosenCommand = getInput();
        Menu nextMenu = null;
        if (chosenCommand == 1)
            nextMenu = submenus.get(0);
        if (chosenCommand == 3)
            nextMenu = getParentMenu();
        else if (chosenCommand == 2) {
            removeProduct();
            nextMenu = this;
        }
        nextMenu.execute();
    }

    private void removeProduct() {
        System.out.println("enter product id that you want remove: ");
        String productID = getValidInput("\\d+{1,15}", "invalid id format");
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeProduct(productID);
            System.out.println("product removed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
