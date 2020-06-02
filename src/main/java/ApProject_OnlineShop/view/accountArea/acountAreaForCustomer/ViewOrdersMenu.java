package ApProject_OnlineShop.view.accountArea.acountAreaForCustomer;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.*;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.exception.userExceptions.NotValidInput;
import ApProject_OnlineShop.view.LoginRegisterMenu;
import ApProject_OnlineShop.view.Menu;

import java.io.IOException;
import java.util.regex.Pattern;

public class ViewOrdersMenu extends Menu {
    public ViewOrdersMenu(Menu parentMenu) {
        super("ApProject_OnlineShop.view orders", parentMenu);
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("show all details of an order");
        commandNames.add("rate a product");
        commandNames.add("sort orders");
    }

    @Override
    public void execute() {
        viewOrders();
        help();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand <= submenus.size())
            nextMenu = submenus.get(chosenCommand - 1);
        else if (chosenCommand == submenus.size() + commandNames.size() + 1)
            nextMenu = parentMenu;
        else {
            if (chosenCommand == submenus.size() + 1)
                showOrderById();
            if (chosenCommand == submenus.size() + 2)
                rateProduct();
            if (chosenCommand == submenus.size() + 3)
                sortOrders();
            nextMenu = this;
            System.out.println("press enter to continue");
            scanner.nextLine();
        }
        nextMenu.execute();
    }

    private void viewOrders(){
        System.out.println("----------------------\nyour orders:\n");
        for (String order : MainController.getInstance().getAccountAreaForCustomerController().getBriefSummeryOfOrders()) {
            System.out.println(order);
        }
        sortOrders();
    }

    private void sortOrders(){
        System.out.println("you can sort list by following items:\n1-date\n2-price\n3-continue");
        int input = Integer.parseInt(getValidInput("^[1-3]$", "not valid input"));
        if(input==3)
            return;
        for (String order : MainController.getInstance().getAccountAreaForCustomerController().getSortedCustomerOrders(input)) {
            System.out.println(order);
        }
    }

    private void showOrderById() {
        try {
            long orderId = getOrderId();
            System.out.println(MainController.getInstance().getAccountAreaForCustomerController().viewAnOrder(orderId));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void rateProduct() {
        try {
            long productId = getProductId();
            getRate(productId);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private long getOrderId() throws Exception {
        System.out.println("enter order ID :");
        String input = scanner.nextLine();
        if (!Pattern.matches("[\\d]+", input))
            throw new NotValidInput();
        if (!MainController.getInstance().getAccountAreaForCustomerController().existOrderById(Long.parseLong(input)))
            throw new NotFoundOrderById();
        return Long.parseLong(input);
    }

    private long getProductId() throws Exception {
        System.out.println("enter product ID :");
        String input = scanner.nextLine();
        if (!Pattern.matches("[\\d]+", input))
            throw new NotValidInput();
        if (!MainController.getInstance().getAccountAreaForCustomerController().checkExistProduct(Long.parseLong(input)))
            throw new ProductWithThisIdNotExist();
        if (!MainController.getInstance().getAccountAreaForCustomerController().hasBuyProduct(Long.parseLong(input)))
            throw new NotBuyProductByThisId();
        return Long.parseLong(input);
    }

    private void getRate(long productId) throws NotValidInput, IOException, FileCantBeSavedException {
        System.out.println("enter a number between 1 to 10 :");
        String input = scanner.nextLine();
        if (!Pattern.matches("[\\d]{1,2}", input))
            throw new NotValidInput();
        if (Integer.parseInt(input) > 10)
            throw new NotValidInput();
        MainController.getInstance().getAccountAreaForCustomerController().rateProduct(productId, Integer.parseInt(input));
    }
}
