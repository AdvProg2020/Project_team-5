package ApProject_OnlineShop.view.accountArea.acountAreaForCustomer;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.NotEnoughCredit;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.view.Menu;
import ApProject_OnlineShop.view.ScreenClearing;

import java.io.IOException;
import java.util.ArrayList;

public class PurchaseMenu extends Menu {
    private boolean backFlag;
    private String usedDiscountCode = null;

    public PurchaseMenu(Menu parentMenu) {
        super("purchase", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("continue");
    }

    @Override
    public void execute() {
        help();
        backFlag = false;
        ArrayList<String> customerInfo = new ArrayList<>();
        long totalPrice = 0;
        getContinue();
        if (!backFlag) {
            customerInfo = receiveInformation();
            successfulPageRun();
        }
        if (!backFlag) {
            totalPrice = getDiscountCode();
            successfulPageRun();
        }
        if (!backFlag) {
            payment(customerInfo, totalPrice);
        }
        parentMenu.execute();
    }

    private void successfulPageRun() {
        help();
        getContinue();
        ScreenClearing.clearScreen();
    }

    private void getContinue() {
        int chosenCommand = getInput();
        if (chosenCommand == 1)
            return;
        if (chosenCommand == 2) {
            backFlag = true;
            return;
        }
        System.out.println("not valid input");
        help();
        getContinue();
    }

    public ArrayList<String> receiveInformation() {
        ArrayList<String> customerInfo = new ArrayList<>();
        System.out.println("enter name :");
        customerInfo.add(getValidInput("[a-zA-Z\\s]+", "not valid name"));
        System.out.println("enter postcode :");
        customerInfo.add(getValidInput("[\\d]+", "not valid postcode"));
        System.out.println("enter address :");
        customerInfo.add(scanner.nextLine());
        System.out.println("enter phone number :");
        customerInfo.add(getValidInput("[\\d]{11}", "not valid phone number"));
        return customerInfo;
    }

    public long getDiscountCode() {
        long totalPrice = MainController.getInstance().getAccountAreaForCustomerController().finalPriceOfAList(Shop.getInstance().getCart());
        System.out.println("enter discount code : (if you don't have discount code enter 0)");
        String discountCode = scanner.nextLine();
        try {
            MainController.getInstance().getAccountAreaForCustomerController().checkValidDiscountCode(discountCode);
            totalPrice = MainController.getInstance().getAccountAreaForCustomerController().useDiscountCode(discountCode);
            usedDiscountCode = discountCode;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            notValidDiscountCode();
        }
        return totalPrice;
    }

    public void notValidDiscountCode() {
        System.out.println("1- next step \n2- use discountCode");
        String choice = scanner.nextLine();
        if (choice.equals("1"))
            return;
        if (choice.equals("2"))
            getDiscountCode();
        else
            System.out.println("not valid input");
    }

    public void payment(ArrayList<String> customerInfo, long totalPrice) {
        System.out.println("total price is " + totalPrice + " Rial");
        System.out.println("press enter to continue");
        scanner.nextLine();
        try {
            MainController.getInstance().getAccountAreaForCustomerController().purchase(totalPrice, customerInfo, usedDiscountCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
