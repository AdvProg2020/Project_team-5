package view.accountArea.AccountAreaForSeller;

import controller.MainController;
import exception.ProductNotFoundExceptionForSeller;
import view.Menu;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ViewOffsMenu extends Menu {
    public ViewOffsMenu(Menu parentMenu) {
        super("view offs", parentMenu);
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 4)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                viewOff();
            if (chosenCommand == 2)
                editOff();
            if (chosenCommand == 3)
                addOff();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    @Override
    public void setCommandNames() {
        commandNames.add("- view off by ID");
        commandNames.add("- edit off");
        commandNames.add("- add off");
    }

    private void viewOff() {
        try {
            System.out.println("Enter off ID :");
            String offDetails = MainController.getInstance().getAccountAreaForSellerController().
                    viewOff(Long.parseLong(getValidInput("[0-9]+", "Not valid off ID")));
            System.out.println(offDetails);
        } catch (ProductNotFoundExceptionForSeller exception) {
            System.out.println(exception);
        }
    }

    private void editOff() {

    }

    private void addOff() {
        ArrayList<String> offDetails = new ArrayList<>();
        System.out.println("start date : \t enter in format[2020-4-27]");
        offDetails.add(getDate());
        System.out.println("end date : \t enter in format[2020-4-27]");
        offDetails.add(getDate());
        System.out.println("maximum amount of purchase :");
        offDetails.add(getValidInput("\\d\\d\\d\\d+", "Not valid amount"));
        System.out.println("discount percent :");
        offDetails.add(getValidInput("[\\d]{1,2}", "Not valid input"));
        MainController.getInstance().getAccountAreaForSellerController().addOff(offDetails, getProductIds());
    }

    private ArrayList<Long> getProductIds() {
        System.out.println("Enter number of product that will contain this off");
        int productNumber = Integer.parseInt(getValidInput("[\\d]{1,4}", "Not valid input"));
        if (!MainController.getInstance().getAccountAreaForSellerController().checkValidProductNumber(productNumber)) {
            System.out.println("You does not have this amount of product");
            getProductIds();
        }
        ArrayList<Long> productIds = new ArrayList<>();
        for (int i = 0; i < productNumber; ) {
            System.out.println("Enter product ID :");
            long productId = Long.parseLong(getValidInput("[\\d]+", "Not valid input"));
            if (!MainController.getInstance().getAccountAreaForSellerController().checkValidProductId(productId))
                System.out.println("This product does not exist your active goods");
            else {
                productIds.add(productId);
                i++;
            }
        }
        return productIds;
    }

    private String getDate() {
        while (true) {
            String date = scanner.nextLine().trim();
            if (Pattern.matches("([\\d]{4})-([\\d]{2})-([\\d]{2})", date)) {
                if (MainController.getInstance().getAccountAreaForSellerController().checkValidDate(date))
                    return date;
            }
            System.out.println("Not valid Date");
        }
    }
}
