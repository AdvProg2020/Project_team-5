package view.accountArea.acountAreaForCustomer;

import controller.MainController;
import exception.*;
import view.LoginRegisterMenu;
import view.Menu;

import java.util.regex.Pattern;

public class ViewOrdersMenu extends Menu {
    public ViewOrdersMenu(Menu parentMenu) {
        super("view orders", parentMenu);
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("- show all details of an order");
        commandNames.add("- rate a product");
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
                showOrderById();
            if (chosenCommand == submenus.size() + 2)
                rateProduct();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void showOrderById(){
        try {
            long orderId = getOrderId();
            System.out.println(MainController.getInstance().getAccountAreaForCustomerController().viewAnOrder(orderId));
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void rateProduct(){
        try {
            long productId = getProductId();
            getRate(productId);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private long getOrderId() throws Exception{
        System.out.println("enter order ID :");
        String input = scanner.nextLine();
        if (!Pattern.matches("[\\d]+", input))
            throw new NotValidInput();
        if (!MainController.getInstance().getAccountAreaForCustomerController().existOrderById(Long.parseLong(input)))
            throw new NotFoundOrderById();
        return Long.parseLong(input);
    }

    private long getProductId() throws Exception{
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

    private void getRate(long productId) throws NotValidInput {
        System.out.println("enter a number between 1 to 10 :");
        String input = scanner.nextLine();
        if (!Pattern.matches("[\\d]{1,2}",input))
            throw new NotValidInput();
        if (Integer.parseInt(input) > 10)
            throw new NotValidInput();
        MainController.getInstance().getAccountAreaForCustomerController().rateProduct(productId, Integer.parseInt(input));
    }
}