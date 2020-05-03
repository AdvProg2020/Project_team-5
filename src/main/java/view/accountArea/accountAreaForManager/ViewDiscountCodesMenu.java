package view.accountArea.accountAreaForManager;

import controller.MainController;
import exception.DiscountCodeCantBeEditedException;
import exception.DiscountCodeNotFoundException;
import view.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("please enter code that you want edit: ");
        String code = getValidInput("\\w+", "invalid code format.");
        try {
            System.out.println("fields for edit :\n-startDate\n-endDate\n-maxDiscountAmount\n-discountPercent");
            System.out.println("enter field for edit and its value: (fieldName newValue)");
            System.out.println("after edit fields, enter [end] to return.");
            Pattern pattern = Pattern.compile("(\\w+) (\\w+)");
            String input, field, newValue;
            Scanner scanner = new Scanner(System.in);
            while (!(input = scanner.nextLine()).equalsIgnoreCase("end")) {
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    field = matcher.group(1);
                    newValue = matcher.group(2);
                    try {
                        MainController.getInstance().getAccountAreaForManagerController().editDiscountCode(code, field, newValue);
                    } catch (DiscountCodeCantBeEditedException e) {
                        System.out.println(e.getMessage());
                    }
                } else
                    System.out.println("invalid input format.");
            }
        } catch (DiscountCodeNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeDiscountCode() {
        System.out.println("enter code that you want remove: ");
        String code = getValidInput("\\w+", "invalid code format.");
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeDiscountCode(code);
            System.out.println("discount code removed successfully.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
