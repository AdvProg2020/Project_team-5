package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu parentMenu) {
        super("manage categories", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("edit");
        this.commandNames.add("add");
        this.commandNames.add("remove");
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
        MainController.getInstance().getAccountAreaForManagerController().addCategory(name, properties);
        System.out.println("category added successfully.");
    }

    private void removeCategory() {
        System.out.println("enter category name that you want delete:");
        String name = getValidInput("\\w+", "invalid name format.");
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeCategory(name);
            System.out.println("category removed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void editCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter category name that you want edit:");
        String name = getValidInput("\\w+", "invalid name format.");
        do {
            System.out.print("enter category name: ");
            name = getValidInput("\\w+", "invalid name format");
        } while (MainController.getInstance().getAccountAreaForManagerController().isExistCategoryWithThisName(name));
        System.out.println("category properties to edit:");
        for (String property : MainController.getInstance().getAccountAreaForManagerController().getCategoryProperties(name)) {
            System.out.println(property);
        }
        System.out.println("enter property name to edit and new value of it and at last enter [end]: (name value)");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("end")) {
            Matcher matcher = Pattern.compile("(\\w+) (\\w+)").matcher(input);
            if (matcher.find()) {
                String property = matcher.group(1);
                String newValue = matcher.group(2);
                try {
                    MainController.getInstance().getAccountAreaForManagerController().editCategory(name, property, newValue);
                    System.out.println("property edited successfully.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else
                System.out.println("invalid input format.");
        }
    }

    //add subcat, edit subcat, ...
}
