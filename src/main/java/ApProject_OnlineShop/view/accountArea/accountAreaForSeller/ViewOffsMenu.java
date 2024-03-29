//package ApProject_OnlineShop.view.accountArea.accountAreaForSeller;
//
//import ApProject_OnlineShop.controller.MainController;
//import ApProject_OnlineShop.exception.OffNotFoundException;
//import ApProject_OnlineShop.model.Shop;
//import ApProject_OnlineShop.view.LoginRegisterMenu;
//import ApProject_OnlineShop.view.Menu;
//
//import java.util.ArrayList;
//import java.util.regex.Pattern;
//
//public class ViewOffsMenu extends Menu {
//    public ViewOffsMenu(Menu parentMenu) {
//        super("ApProject_OnlineShop.view offs", parentMenu);
//        submenus.add(new LoginRegisterMenu(this));
//    }
//
//    @Override
//    public void execute() {
//        viewAllOffs();
//        help();
//        int chosenCommand = getInput();
//        Menu nextMenu;
//        if (chosenCommand <= submenus.size())
//            nextMenu = submenus.get(chosenCommand - 1);
//        else if (chosenCommand == submenus.size() + commandNames.size() + 1)
//            nextMenu = getParentMenu();
//        else {
//            if (chosenCommand == submenus.size() + 1)
//                viewOff();
//            if (chosenCommand == submenus.size() + 2)
//                editOff();
//            if (chosenCommand == submenus.size() + 3)
//                addOff();
//            if (chosenCommand == submenus.size() + 4)
//                sortOffs();
//            nextMenu = this;
//        }
//        nextMenu.execute();
//    }
//
//    @Override
//    public void setCommandNames() {
//        commandNames.add("ApProject_OnlineShop.view off by ID");
//        commandNames.add("edit off");
//        commandNames.add("add off");
//        commandNames.add("sort offs list");
//    }
//
//    private void viewAllOffs() {
//        System.out.println("-----------------\nyour offs:");
//        for (String off : MainController.getInstance().getAccountAreaForSellerController().getAllOffs()) {
//            System.out.println(off);
//        }
//        sortOffs();
//    }
//
//    private void sortOffs() {
//        System.out.println("you can sort list by following items:\n1-end date\n2-off percent\n3-maximum discount\n4-continue");
//        int input = Integer.parseInt(getValidInput("^[1-4]$", "not valid input"));
//        if (input == 4)
//            return;
//        for (String off : MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(input)) {
//            System.out.println(off);
//        }
//    }
//
//    private void viewOff() {
//        try {
//            System.out.println("Enter off ID :");
//            String offDetails = MainController.getInstance().getAccountAreaForSellerController().
//                    viewOff(Long.parseLong(getValidInput("[0-9]+", "Not valid off ID")));
//            System.out.println(offDetails);
//        } catch (OffNotFoundException exception) {
//            System.out.println(exception.getMessage());
//            exception.printStackTrace();
//        }
//    }
//
//    private void editOff() {
//        String input;
//        while (true) {
//            System.out.println("Enter off ID :");
//            input = getValidInput("[0-9]+", "Not valid off ID");
//            if (MainController.getInstance().getAccountAreaForSellerController().doesSellerHaveThisOff(Long.parseLong(input)))
//                break;
//            else
//                System.out.println("this isn't valid off ID\nif you want to quit type back or press enter to continue");
//            if (scanner.nextLine().equalsIgnoreCase("back"))
//                return;
//        }
//        long id = Long.parseLong(input);
//        System.out.println("choose one to edit");
//        printEditableFields();
//        int chosen = Integer.parseInt(getValidInput("^[1-6]{1}$", "not invalid input"));
//        if (chosen == 1) {
//            editStartDate(id);
//        } else if (chosen == 2) {
//            editEndDate(id);
//        } else if (chosen == 3) {
//            editMaxDiscount(id);
//        } else if (chosen == 4) {
//            editDiscountPercent(id);
//        } else if (chosen == 5) {
//            addGoodToOff(id);
//        } else if (chosen == 6) {
//            removeGoodFromOff(id);
//        }
//        System.out.println("press enter to continue");
//        scanner.nextLine();
//    }
//
//    private void printEditableFields() {
//        System.out.println("1-start date\n2-end date\n3-max discount of off\n4-discount percent\n5-add a good to off\n6-remove a good from off");
//    }
//
//    private void addOff() {
//        ArrayList<String> offDetails = new ArrayList<>();
//        System.out.println("start date : \t enter in format[2020-04-27]");
//        offDetails.add(getDate(0, ""));
//        System.out.println("end date : \t enter in format[2020-04-27]");
//        offDetails.add(getDate(1, offDetails.get(0)));
//        System.out.println("maximum amount of purchase :");
//        offDetails.add(getValidInput("\\d\\d\\d\\d+", "Not valid amount"));
//        System.out.println("discount percent :");
//        offDetails.add(getValidInput("[\\d]{1,2}", "Not valid input"));
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().addOff(offDetails, getProductIdsForAddingOff());
//            System.out.println("your request successfully sent to manager");
//            System.out.println("press enter to continue");
//            scanner.nextLine();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private ArrayList<Long> getProductIdsForAddingOff() {
//        int productNumber;
//        do {
//            System.out.println("Enter number of product that will contain this off");
//            productNumber = Integer.parseInt(getValidInput("[\\d]{1,4}", "Not valid input"));
//            if (!MainController.getInstance().getAccountAreaForSellerController().checkValidProductNumber(productNumber)){
//                System.out.println("You does not have this amount of product");
//            }else{
//                break;
//            }
//        }while (true);
//        ArrayList<Long> productIds = new ArrayList<>();
//        for (int i = 0; i < productNumber; ) {
//            System.out.println("Enter product ID :");
//            long productId = Long.parseLong(getValidInput("[\\d]+", "Not valid input"));
//            if (!MainController.getInstance().getAccountAreaForSellerController().checkValidProductId(productId))
//                System.out.println("This product does not exist your active goods");
//            else {
//                productIds.add(productId);
//                i++;
//            }
//        }
//        return productIds;
//    }
//
//    private String getDate(int a, String startDate) {
//        while (true) {
//            String date = scanner.nextLine().trim();
//            if (Pattern.matches("(\\d{4}-\\d{2}-\\d{2})", date)) {
//                if (MainController.getInstance().getAccountAreaForSellerController().checkValidDate(date, a, startDate))
//                    return date;
//            }
//            System.out.println("Not valid Date");
//        }
//    }
//
//    private void editStartDate(long id) {
//        System.out.println("enter a date in format [2020-04-27]");
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().editOff("start date", getDate(0, ""), id);
//            System.out.println("your request successfully sent to manager");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void editEndDate(long id) {
//        System.out.println("enter a date in format [2020-04-27]");
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().editOff("end date", getDate(1,
//                    Shop.getInstance().findOffById(id).getStartDate().toString()), id);
//            System.out.println("your request successfully sent to manager");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void editMaxDiscount(long id) {
//        System.out.println("enter maximum discount you want for you off");
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().editOff("max discount",
//                    getValidInput("\\d\\d\\d\\d+", "Not valid amount"), id);
//            System.out.println("your request successfully sent to manager");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void editDiscountPercent(long id) {
//        System.out.println("enter discount percent you want for you off");
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().editOff("discount percent",
//                    getValidInput("[\\d]{1,2}", "Not valid percent"), id);
//            System.out.println("your request successfully sent to manager");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void addGoodToOff(long id) {
//        System.out.println("enter good id to add to your off");
//        long productId = Long.parseLong(getValidInput("[\\d]+", "Not valid id"));
//        if (Shop.getInstance().findGoodById(productId) == null) {
//            System.out.println("doesn't exist a product with this id");
//            return;
//        } else if (!MainController.getInstance().getAccountAreaForSellerController().checkValidProductId(productId)) {
//            System.out.println("This product does not exist your active goods");
//            return;
//        } else if (Shop.getInstance().findOffById(id).doesHaveThisProduct(Shop.getInstance().findGoodById(productId))) {
//            System.out.println("you have this product in your off already");
//            return;
//        }
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().editOff("add good", "" + productId, id);
//            System.out.println("your request successfully sent to manager");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void removeGoodFromOff(long id) {
//        System.out.println("enter good id to remove from your off");
//        long productId = Long.parseLong(getValidInput("[\\d]+", "Not valid id"));
//        if (!Shop.getInstance().findOffById(id).doesHaveThisProduct(Shop.getInstance().findGoodById(productId))) {
//            System.out.println("you don't have this product in your off");
//            return;
//        }
//        try {
//            MainController.getInstance().getAccountAreaForSellerController().editOff("remove good", "" + productId, id);
//            System.out.println("your request successfully sent to manager");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
