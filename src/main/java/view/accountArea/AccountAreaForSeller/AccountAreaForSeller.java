package view.accountArea.AccountAreaForSeller;

import controller.MainController;
import exceptions.NotHaveThisProduct;
import model.Shop;
import model.orders.OrderForCustomer;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.Good;
import view.LoginRegisterMenu;
import view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AccountAreaForSeller extends Menu {

    public AccountAreaForSeller(Menu parentMenu) {
        super("Account area for seller", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("-view company information");
        this.commandNames.add("-view sales history");
        this.commandNames.add("-add product");
        this.commandNames.add("-remove product");
        this.commandNames.add("-show categories");
        this.commandNames.add("-view balance");
    }


    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand <= submenus.size())
            nextMenu = submenus.get(chosenCommand - 1);
        if (chosenCommand == submenus.size() + commandNames.size() + 1)
            nextMenu = parentMenu;
        else {
            if (chosenCommand == submenus.size() + 1)
                viewCompanyInfo();
            if (chosenCommand == submenus.size() + 2)
                viewSalesHistory();
            if (chosenCommand == submenus.size() + 3)
                addProduct();
            if (chosenCommand == submenus.size() + 4)
                removeProduct();
            if (chosenCommand == submenus.size() + 5)
                showCategories();
            if (chosenCommand == submenus.size() + 6)
                viewBalance();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void viewCompanyInfo() {
        System.out.println(MainController.getInstance().getAccountAreaForSellerController().getCompanyInfo());
    }

    private void viewSalesHistory() {
        List<String> salesLog = MainController.getInstance().getAccountAreaForSellerController().getSalesLog();
        for (String log : salesLog) {
            System.out.println(log);
        }
    }

    private void addProduct(){

    }

    private void removeProduct(){
        long productId = Long.parseLong(getValidInput("[0-9]+", "Enter productId :"));
        try {
            MainController.getInstance().getAccountAreaForSellerController().removeProduct(productId);
            System.out.println("product removed successfully");
        }catch (NotHaveThisProduct exception){
            System.out.println(exception.getMessage());
        }
    }

    private void showCategories(){
        ArrayList<String> categories = MainController.getInstance().getAccountAreaForSellerController().showCategories();
        for (String category : categories) {
            System.out.print(category);
        }
    }

    private void viewBalance(){
        System.out.println(MainController.getInstance().getAccountAreaForSellerController().viewBalance());
    }
}