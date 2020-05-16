package view.accountArea.accountAreaForSeller;

import controller.MainController;
import exception.productExceptions.ProductNotFoundExceptionForSeller;
import model.Shop;
import view.LoginRegisterMenu;
import view.Menu;

import java.util.ArrayList;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("manage products", parentMenu);
        this.submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    public void execute() {
        viewSellerProduts();
        help();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 1)
            nextMenu = submenus.get(0);
        else if (chosenCommand == 5)
            nextMenu = parentMenu;
        else {
            if (chosenCommand == 2)
                viewProduct();
            if (chosenCommand == 3)
                viewBuyers();
            if (chosenCommand == 4)
                editProduct();
            nextMenu = this;
        }
        nextMenu.execute();
    }

    @Override
    public void setCommandNames() {
        commandNames.add("view product");
        commandNames.add("view buyers");
        commandNames.add("edit product");
    }

    private void viewSellerProduts() {
        System.out.println(MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(0));
        System.out.println("you can sort this list by following items:\n1-visit number\n2-average rate\n" +
                "3-modification date\n4-price\n5-available number\n6-continue");
        int input = Integer.parseInt(getValidInput("^[1-6]$", "not valid input enter number between 1 and 6"));
        if (input == 6)
            return;
        System.out.println(MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(input));
    }

    private void viewProduct() {
        try {
            String product = MainController.getInstance().getAccountAreaForSellerController().viewProduct(getProductId());
            System.out.println(product);
        } catch (ProductNotFoundExceptionForSeller exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void viewBuyers() {
        try {
            ArrayList<String> buyers = MainController.getInstance().getAccountAreaForSellerController().buyersOfProduct(getProductId());
            for (String buyer : buyers) {
                System.out.println(buyer);
            }
        } catch (ProductNotFoundExceptionForSeller exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void editProduct() {
        long input = getProductId();
        try {
            MainController.getInstance().getAccountAreaForSellerController().viewProduct(input);
        } catch (ProductNotFoundExceptionForSeller exception) {
            System.out.println(exception.getMessage());
            return;
        }
        System.out.println("choose one to edit");
        printEditableFields(input);
        while (true) {
            int chosen = Integer.parseInt(getValidInput("^[1-10]{1}$", "not invalid input"));
            if (chosen <= 3 + Shop.getInstance().findGoodById(input).getCategoryProperties().keySet().size())
                break;
            else
                System.out.println("not invalid input");
            System.out.println("if you want to return type back");
            if (scanner.nextLine().equalsIgnoreCase("back"))
                return;
        }
        int chosen = Integer.parseInt(getValidInput("^[1-3]{1}$", "not invalid input"));
        if (chosen == 1) {
            editPriceOfProduct(input);
        } else if (chosen == 2) {
            editAvailableNumber(input);
        } else if (chosen == 3) {
            editDetails(input);
        } else {
            editCategoryProperty(chosen - 4, input);
        }
        System.out.println("press enter to continue");
        scanner.nextLine();
    }

    private void printEditableFields(long id) {
        System.out.println("you can edit below fields of this product:\n1-price\n2-available number\n3-product details");
        int i = 4;
        for (String s : Shop.getInstance().findGoodById(id).getCategoryProperties().keySet()) {
            System.out.println((i++) + "-" + Shop.getInstance().findGoodById(id).getCategoryProperties().get(s));
        }
    }

    private void editPriceOfProduct(long id) {
        System.out.println("enter new price");
        MainController.getInstance().getAccountAreaForSellerController().
                editProduct("price", getValidInput("\\d\\d\\d\\d+", "Not valid price"), id);
        System.out.println("your request successfully sent to manager");
    }

    private void editAvailableNumber(long id) {
        System.out.println("enter new available number");
        MainController.getInstance().getAccountAreaForSellerController().
                editProduct("availableNumber", getValidInput("\\d+", "Not valid number"), id);
        System.out.println("your request successfully sent to manager");
    }

    private void editDetails(long id) {
        System.out.println("enter new details");
        MainController.getInstance().getAccountAreaForSellerController().
                editProduct("details", getValidInput("\\w+", "Not valid detail"), id);
        System.out.println("your request successfully sent to manager");
    }

    private void editCategoryProperty(int number, long id) {
        System.out.println("enter new property for " + Shop.getInstance().findGoodById(id).getCategoryProperties().keySet().toArray()[number]);
        MainController.getInstance().getAccountAreaForSellerController().
                editProduct((String) Shop.getInstance().findGoodById(id).getCategoryProperties().keySet().toArray()[number]
                        , getValidInput("\\w+", "Not valid!"), id);
        System.out.println("your request successfuly sent to manager");
    }

    public long getProductId() {
        System.out.println("Enter product ID :");
        return Long.parseLong(getValidInput("[0-9]+", "Not valid product ID"));
    }
}
