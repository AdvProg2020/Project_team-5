package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

import java.util.ArrayList;

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
        showAllCategories();
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
        for (String category : MainController.getInstance().getAccountAreaForManagerController().getAllCategories()) {
            System.out.println(category);
        }
    }

    private void addCategory() {
        String name;
        ArrayList<String> properties = new ArrayList<>();
        do {
            System.out.print("enter category name: ");
            name = getValidInput("\\w+", "invalid name format");
        } while (MainController.getInstance().getAccountAreaForManagerController().isExistCategoryWithThisName(name));
        System.out.println("now enter name of category properties, each property in a separate line. (enter [end] to finish)");
        String field;
        while (!(field = getValidInput("\\w+", "invalid name format")).equalsIgnoreCase("end"))
            properties.add(field);
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
