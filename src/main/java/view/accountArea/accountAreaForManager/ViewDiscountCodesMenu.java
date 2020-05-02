package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

public class ViewDiscountCodesMenu extends Menu {
    public ViewDiscountCodesMenu(Menu parentMenu) {
        super("view discount codes", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("- view discount code");
        this.commandNames.add("- edit discount code");
        this.commandNames.add("- remove discount code");
    }

    @Override
    public void execute() {
        printAllDiscountCodes();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 4)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                viewDiscountCode();
            if (chosenCommand == 2)
                editDiscountCode();
            if (chosenCommand == 3)
                removeDiscountCode();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void printAllDiscountCodes() {
        for (String discountCode : MainController.getInstance().getAccountAreaForManagerController().getAllDiscountCodesInfo()) {
            System.out.println(discountCode);
        }
    }

    private void viewDiscountCode() {
        System.out.println("enter code that you want view: ");
        String code = getValidInput("\\w+", "invalid code format.");
        try {
            System.out.println(MainController.getInstance().getAccountAreaForManagerController().viewDiscountCode(code));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void editDiscountCode() {

    }

    private void removeDiscountCode() {

    }
}
