package view.accountArea.accountAreaForManager;

import controller.MainController;
import exception.discountcodeExceptions.DiscountCodeCantCreatedException;
import exception.discountcodeExceptions.DiscountCodeNotFoundException;
import exception.UsernameNotFoundException;
import view.Menu;
import view.accountArea.ViewingPersonalInfo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountAreaForManager extends Menu {
    public AccountAreaForManager(Menu parentMenu) {
        super("Account area for manager", parentMenu);
        this.submenus.add(new ViewingPersonalInfo(this));
        this.submenus.add(new ManageUsersMenu(this));
        this.submenus.add(new ManageAllProductsMenu(this));
        this.submenus.add(new ViewDiscountCodesMenu(this));
        this.submenus.add(new ManageRequestsMenu(this));
        this.submenus.add(new ManageCategoriesMenu(this));
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("create discount code");
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
        try {
            MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(inputFields);
            getIncludedCustomers(inputFields.get(0));
            System.out.println("discount code created successfully.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getIncludedCustomers(String code)
        throws UsernameNotFoundException, DiscountCodeNotFoundException, DiscountCodeCantCreatedException {
        Pattern pattern = Pattern.compile("(\\w+) (\\d+)");
        String input, username, numberOfUse;
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter customer and number of use for each customer: -> (#username #number)");
        System.out.println("if all customers inserted, enter [end]");
        while (!(input = scanner.nextLine()).equalsIgnoreCase("end")) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                username = matcher.group(1);
                numberOfUse = matcher.group(2);
                MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode(code, username, numberOfUse);
            } else
                System.out.println("invalid input format.");
        }
    }
}
