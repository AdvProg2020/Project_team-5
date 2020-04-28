package view.accountArea.accountAreaForManager;

import view.Menu;

import java.util.ArrayList;

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
        ArrayList<String> inputFields = new ArrayList<>();
        System.out.println("enter code");
        inputFields.add(getValidInput("\\w{1,15}", "this code has invalid format."));
        System.out.println("enter start date (yyyy-mm-dd):");
        inputFields.add(getValidInput("\\d{4}-\\d{2}-\\d{2}", "invalid date format"));
        System.out.println("enter end date (yyyy-mm-dd):");
        inputFields.add(getValidInput("\\d{4}-\\d{2}-\\d{2}", "invalid date format"));
        System.out.println("enter discount amount: (at most 16 digits)");
        inputFields.add(getValidInput("\\d{1,15}", "invalid discount amount"));
        System.out.println("enter discount percent:");
        inputFields.add(getValidInput("\\d{1,2}", "invalid discount percent"));
        //TODO
    }

    private void getIncludedCustomers() {
        //TODO
    }
}
