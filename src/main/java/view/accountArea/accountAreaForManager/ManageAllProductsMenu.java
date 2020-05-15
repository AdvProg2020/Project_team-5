package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Menu parentMenu) {
        super("manage all products", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("- remove");
    }

    @Override
    public void execute() {
        help();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 2)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                removeProduct();
            nextMenu = this;
        }
        nextMenu.execute();
    }

    private void removeProduct() {
        System.out.println("enter product id that you want remove: ");
        String productID = getValidInput("\\d+{,15}", "invalid id format");
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeProduct(productID);
            System.out.println("product removed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
