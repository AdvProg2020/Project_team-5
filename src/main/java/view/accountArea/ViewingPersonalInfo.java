package view.accountArea;

import controller.MainController;
import view.LoginRegisterMenu;
import view.Menu;

public class ViewingPersonalInfo extends Menu {

    public ViewingPersonalInfo(Menu parentMenu) {
        super("view personal info", parentMenu);
        this.submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("edit field");
    }

    @Override
    public void execute() {
        showPersonalInfo();
        help();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 1)
            nextMenu = submenus.get(0);
        else if (chosenCommand == 3)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 2)
                editField();
            nextMenu = this;
        }
        nextMenu.execute();
    }

    private void showPersonalInfo() {
        System.out.println(MainController.getInstance().getAccountAreaForManagerController().getUserPersonalInfo());
    }

    private void editField() {
        System.out.println("choose one of these fields to edit: (enter number)");
        printEditableFields();
        int chosenField = getInput();
        System.out.print("enter new value: ");
        String newValue = getValidInput(".+", "invalid input");
        try {
            MainController.getInstance().getAccountAreaForManagerController().editField(chosenField, newValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printEditableFields() {
        System.out.println("editable fields ->");
        System.out.println("1-first name\n2-last name\n3-email\n4-phone number\n5-password");
    }
}
