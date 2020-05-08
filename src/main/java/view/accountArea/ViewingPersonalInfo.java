package view.accountArea;

import controller.MainController;
import view.Menu;

import java.util.ArrayList;

public class ViewingPersonalInfo extends Menu {

    public ViewingPersonalInfo(Menu parentMenu) {
        super("view personal info", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("edit field");
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 2)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                editField();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void showPersonalInfo() {
        System.out.println(MainController.getInstance().getAccountAreaForManagerController().getUserPersonalInfo());
    }

    private void editField() {

    }
}
