package view.accountArea.accountAreaForManager;

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
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 2)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                removeProduct();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void removeProduct() {

    }
}
