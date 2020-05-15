package view.accountArea.acountAreaForCustomer;

import controller.MainController;
import view.LoginRegisterMenu;
import view.Menu;
import view.accountArea.ViewingPersonalInfo;

public class AccountAreaForCustomer extends Menu {
    public AccountAreaForCustomer(Menu parentMenu) {
        super("Account area for customer", parentMenu);
        submenus.add(new ViewingPersonalInfo(this));
        submenus.add(new ViewCartMenu(this));
        submenus.add(new ViewOrdersMenu(this));
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    public void setCommandNames() {
        commandNames.add("view balance");
        commandNames.add("view discount codes");
    }

    @Override
    public void execute() {
        help();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand <= submenus.size())
            nextMenu = submenus.get(chosenCommand - 1);
        else if (chosenCommand == submenus.size() + commandNames.size() + 1)
            nextMenu = parentMenu;
        else {
            if (chosenCommand == submenus.size() + 1)
                viewBalance();
            if (chosenCommand == submenus.size() + 2)
                viewDiscountCodes();
            nextMenu = this;
            System.out.println("press enter to continue");
            scanner.nextLine();
        }
        nextMenu.execute();
    }

    private void viewBalance() {
        System.out.println("Your remained credit is " + MainController.getInstance().
                getAccountAreaForCustomerController().viewBalance() + " Rial");
    }

    private void viewDiscountCodes() {
        int i = 1;
        for (String discountCode : MainController.getInstance().getAccountAreaForCustomerController().viewDiscountCodes()) {
            System.out.println((i++) + "- " + discountCode);
        }
        System.out.println("you can sort this list by following items:\n1-discount percent\n2-end date\n3-maximum discount amount\n4-continue");
        int input=Integer.parseInt(getValidInput("^[1-4]$","not valid input"));
        if(input == 4)
            return;
        for (String discountCode : MainController.getInstance().getAccountAreaForCustomerController().getSortedDiscountCode(input)) {
            System.out.println(discountCode);
        }
    }
}
