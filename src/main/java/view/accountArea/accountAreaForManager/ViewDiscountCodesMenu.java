package view.accountArea.accountAreaForManager;

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

    private void viewDiscountCode() {

    }

    private void editDiscountCode() {

    }

    private void removeDiscountCode() {

    }
}
