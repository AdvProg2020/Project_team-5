package view.accountArea.AccountAreaForSeller;

import controller.MainController;
import exception.ProductNotFoundException;
import model.Shop;
import model.orders.OrderForCustomer;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.Good;
import view.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountAreaForSeller extends Menu {

    public AccountAreaForSeller(Menu parentMenu) {
        super("Account area for seller", parentMenu);
        submenus.add(new ManageProductsMenu(this));
        submenus.add(new ViewOffsMenu(this));
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

    private void addProduct() {
        ArrayList<String> productDetails = new ArrayList<>();
        System.out.println("name :");
        productDetails.add(scanner.nextLine());
        System.out.println("brand :");
        productDetails.add(scanner.nextLine());
        System.out.println("price :");
        productDetails.add(getValidInput("\\d\\d\\d\\d+", "Not valid price"));
        System.out.println("available number :");
        productDetails.add(getValidInput("[0-9]{1,3}", "Not valid number"));
        System.out.println("Additional details");
        productDetails.add(scanner.nextLine());
        productDetails.add(getCorrectSubCategory());
        MainController.getInstance().getAccountAreaForSellerController().addProduct(productDetails, getDetails(productDetails.get(5)));
    }

    private String getCorrectSubCategory(){
        System.out.println("subcategory :");
        String subCategory = scanner.nextLine();
        if (MainController.getInstance().getAccountAreaForSellerController().isSubCategoryCorrect(subCategory))
            return subCategory;
        System.out.println("This subcategory does not exist");
        return getCorrectSubCategory();
    }

    private HashMap<String, Object> getDetails(String subcategory){
        HashMap<String, Object> detailValues = new HashMap<>();
        for (String detail : MainController.getInstance().getAccountAreaForSellerController().getSubcategoryDetails(subcategory)) {
            System.out.println(detail + " :");
            detailValues.put(detail,scanner.nextLine());
        }
        return detailValues;
    }

    private void removeProduct() {
        System.out.println("Enter product ID :");
        try {
            MainController.getInstance().getAccountAreaForSellerController().removeProduct
                    (Long.parseLong(getValidInput("[0-9]+", "Not valid product ID")));
            System.out.println("product removed successfully");
        } catch (ProductNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void showCategories() {
        ArrayList<String> categories = MainController.getInstance().getAccountAreaForSellerController().showCategories();
        for (String category : categories) {
            System.out.print(category);
        }
    }

    private void viewBalance() {
        System.out.println(MainController.getInstance().getAccountAreaForSellerController().viewBalance() + "Rial");
    }
}