package view.accountArea.accountAreaForManager;

import view.Menu;

public class AccountAreaForManager extends Menu {
    public AccountAreaForManager(Menu parentMenu) {
        super("Account area for manager", parentMenu);
        this.submenus.add(new ManageUsersMenu(this));
        this.submenus.add(new ManageAllProductsMenu(this));
        this.submenus.add(new ViewDiscountCodesMenu(this));
        this.submenus.add(new ManageRequestsMenu(this));
        this.submenus.add(new ManageCategoriesMenu(this));
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("-create discount code");
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand <= submenus.size())
            nextMenu = submenus.get(chosenCommand - 1);
        else if (chosenCommand == submenus.size() + commandNames.size() + 1)
            nextMenu = parentMenu;
        else {
            if (chosenCommand == submenus.size() + 1)
                createDiscountCode();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void createDiscountCode() {

    }
}
