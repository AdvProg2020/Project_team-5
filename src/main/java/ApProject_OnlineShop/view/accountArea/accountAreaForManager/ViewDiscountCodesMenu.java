//package ApProject_OnlineShop.view.accountArea.accountAreaForManager;
//
//import ApProject_OnlineShop.controller.MainController;
//import ApProject_OnlineShop.exception.FileCantBeSavedException;
//import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantBeEditedException;
//import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
//import ApProject_OnlineShop.view.LoginRegisterMenu;
//import ApProject_OnlineShop.view.Menu;
//
//import java.io.IOException;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ViewDiscountCodesMenu extends Menu {
//    public ViewDiscountCodesMenu(Menu parentMenu) {
//        super("view discount codes", parentMenu);
//        this.submenus.add(new LoginRegisterMenu(this));
//    }
//
//    @Override
//    protected void setCommandNames() {
//        this.commandNames.add("view discount code");
//        this.commandNames.add("edit discount code");
//        this.commandNames.add("remove discount code");
//    }
//
//    @Override
//    public void execute() {
//        printAllDiscountCodes();
//        help();
//        int chosenCommand = getInput();
//        Menu nextMenu;
//        if (chosenCommand == 1)
//            nextMenu = submenus.get(0);
//        else if (chosenCommand == 5)
//            nextMenu = getParentMenu();
//        else {
//            if (chosenCommand == 2)
//                viewDiscountCode();
//            if (chosenCommand == 3)
//                editDiscountCode();
//            if (chosenCommand == 4)
//                removeDiscountCode();
//            nextMenu = this;
//        }
//        nextMenu.execute();
//    }
//
//    private void printAllDiscountCodes() {
//        System.out.println("-----------------------");
//        for (String discountCode : MainController.getInstance().getAccountAreaForManagerController().
//                getAllDiscountCodeWithSort(0)) {
//            System.out.println(discountCode);
//        }
//        System.out.println("-----------------------");
//        System.out.println("you can sort this list by following items:\n1-discount percent\n2-end date\n3-maximum discount amount\n4-continue");
//        int input = Integer.parseInt(getValidInput("^[1-4]$", "not valid input"));
//        if (input == 4)
//            return;
//        for (String discountCode : MainController.getInstance().getAccountAreaForManagerController().
//                getAllDiscountCodeWithSort(input)) {
//            System.out.println(discountCode);
//        }
//    }
//
//    private void viewDiscountCode() {
//        System.out.println("enter code that you want ApProject_OnlineShop.view: ");
//        String code = getValidInput("\\w+", "invalid code format.");
//        try {
//            System.out.println(MainController.getInstance().getAccountAreaForManagerController().viewDiscountCode(code));
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    private void editDiscountCode() {
//        System.out.println("please enter code that you want edit: ");
//        String code = getValidInput("\\w+", "invalid code format.");
//        try {
//            System.out.println("fields for edit :\n-startDate\n-endDate\n-maxDiscountAmount\n-discountPercent");
//            System.out.println("enter field for edit and its value: (fieldName newValue)");
//            System.out.println("after edit fields, enter [end] to return.");
//            Pattern pattern = Pattern.compile("(\\S+) (\\S+)");
//            String input, field, newValue;
//            Scanner scanner = new Scanner(System.in);
//            while (!(input = scanner.nextLine()).equalsIgnoreCase("end")) {
//                Matcher matcher = pattern.matcher(input);
//                if (matcher.find()) {
//                    field = matcher.group(1);
//                    newValue = matcher.group(2);
//                    try {
//                        MainController.getInstance().getAccountAreaForManagerController().editDiscountCode(code, field, newValue);
//                        System.out.println("successfully edited!");
//                        System.out.println("press enter to continue");
//                        scanner.nextLine();
//                        break;
//                    } catch (DiscountCodeCantBeEditedException| FileCantBeSavedException | IOException e) {
//                        System.out.println(e.getMessage());
//                    }
//                } else
//                    System.out.println("invalid input format.");
//            }
//        } catch (DiscountCodeNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void removeDiscountCode() {
//        System.out.println("enter code that you want remove: ");
//        String code = getValidInput("\\w+", "invalid code format.");
//        try {
//            MainController.getInstance().getAccountAreaForManagerController().removeDiscountCode(code);
//            System.out.println("discount code removed successfully.");
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//}
