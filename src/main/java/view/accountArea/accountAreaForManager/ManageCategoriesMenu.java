package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu parentMenu) {
        super("manage categories", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("- edit");
        this.commandNames.add("- add");
        this.commandNames.add("- remove");
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 4)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                addCategory();
            if (chosenCommand == 2)
                editCategory();
            if (chosenCommand == 3)
                removeCategory();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void showAllCategories() {

    }

    private void addCategory() {

    }

    private void removeCategory() {
        System.out.println("enter category name that you want delete:");
        String name = getValidInput("\\w+", "invalid name format.");
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeCategory(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void editCategory() {

    }
}
