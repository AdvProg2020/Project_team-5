//package ApProject_OnlineShop.view.accountArea.accountAreaForSeller;
//
//import ApProject_OnlineShop.controller.MainController;
//import ApProject_OnlineShop.exception.productExceptions.ProductNotFoundExceptionForSeller;
//import ApProject_OnlineShop.view.LoginRegisterMenu;
//import ApProject_OnlineShop.view.Menu;
//import ApProject_OnlineShop.view.accountArea.ViewingPersonalInfo;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class AccountAreaForSeller extends Menu {
//
//    public AccountAreaForSeller(Menu parentMenu) {
//        super("Account area for seller", parentMenu);
//        this.submenus.add(new ViewingPersonalInfo(this));
//        this.submenus.add(new ManageProductsMenu(this));
//        this.submenus.add(new ViewOffsMenu(this));
//        this.submenus.add(new LoginRegisterMenu(this));
//    }
//
//    @Override
//    protected void setCommandNames() {
//        this.commandNames.add("ApProject_OnlineShop.view company information");
//        this.commandNames.add("ApProject_OnlineShop.view sales history");
//        this.commandNames.add("add product");
//        this.commandNames.add("remove product");
//        this.commandNames.add("show categories");
//        this.commandNames.add("ApProject_OnlineShop.view balance");
//    }
//
//    @Override
//    public void execute() {
//        help();
//        int chosenCommand = getInput();
//        Menu nextMenu;
//        if (chosenCommand <= submenus.size())
//            nextMenu = submenus.get(chosenCommand - 1);
//        else if (chosenCommand == submenus.size() + commandNames.size() + 1)
//            nextMenu = parentMenu;
//        else {
//            if (chosenCommand == submenus.size() + 1)
//                viewCompanyInfo();
//            if (chosenCommand == submenus.size() + 2)
//                viewSalesHistory();
//            if (chosenCommand == submenus.size() + 3)
//                addProduct();
//            if (chosenCommand == submenus.size() + 4)
//                removeProduct();
//            if (chosenCommand == submenus.size() + 5)
//                showCategories();
//            if (chosenCommand == submenus.size() + 6)
//                viewBalance();
//            nextMenu = this;
//        }
//        nextMenu.execute();
//    }
//
//    private void viewCompanyInfo() {
//        System.out.println(MainController.getInstance().getAccountAreaForSellerController().getCompanyInfo());
//    }
//
//    private void viewSalesHistory() {
//        List<String> salesLog = MainController.getInstance().getAccountAreaForSellerController().getSalesLog();
//        for (String log : salesLog) {
//            System.out.println(log);
//        }
//        System.out.println("you can sort list by following items:\n1-date\n2-price\n3-continue");
//        int input = Integer.parseInt(getValidInput("^[1-3]$", "not valid input"));
//        for (String log : MainController.getInstance().getAccountAreaForSellerController().getSortedLogs(input)) {
//            System.out.println(log);
//        }
//    }
//
//    private void addProduct() {
//        ArrayList<String> productDetails = new ArrayList<>();
//        System.out.println("name :");
//        productDetails.add(scanner.nextLine());
//        System.out.println("brand :");
//        productDetails.add(scanner.nextLine());
//        System.out.println("price :");
//        productDetails.add(getValidInput("\\d\\d\\d\\d+", "Not valid price"));
//        System.out.println("available number :");
//        productDetails.add(getValidInput("[0-9]{1,3}", "Not valid number"));
//        System.out.println("Additional details");
//        productDetails.add(scanner.nextLine());
//        productDetails.add(getCorrectSubCategory());
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().addProduct(productDetails, getDetails(productDetails.get(5)));
//            System.out.println("adding good request successfully sent to manager!");
//            System.out.println("press enter to continue");
//            scanner.nextLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String getCorrectSubCategory() {
//        System.out.println("subcategory :");
//        String subCategory = scanner.nextLine();
//        if (MainController.getInstance().getAccountAreaForSellerController().isSubCategoryCorrect(subCategory))
//            return subCategory;
//        System.out.println("This subcategory does not exist");
//        return getCorrectSubCategory();
//    }
//
//    private HashMap<String, String> getDetails(String subcategory) {
//        HashMap<String, String> detailValues = new HashMap<>();
//        for (String detail : MainController.getInstance().getAccountAreaForSellerController().getSubcategoryDetails(subcategory)) {
//            System.out.println(detail + " :");
//            detailValues.put(detail, scanner.nextLine());
//        }
//        return detailValues;
//    }
//
//    private void removeProduct() {
//        System.out.println("Enter product ID :");
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().removeProduct
//                    (Long.parseLong(getValidInput("[0-9]+", "Not valid product ID")));
//            System.out.println("product removed successfully");
//        } catch (ProductNotFoundExceptionForSeller exception) {
//            System.out.println(exception.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void showCategories() {
//        ArrayList<String> categories = MainController.getInstance().getAccountAreaForSellerController().showCategories();
//        for (String category : categories) {
//            System.out.print(category);
//        }
//    }
//
//    private void viewBalance() {
//        System.out.println(MainController.getInstance().getAccountAreaForSellerController().viewBalance() + "Rial");
//    }
//}