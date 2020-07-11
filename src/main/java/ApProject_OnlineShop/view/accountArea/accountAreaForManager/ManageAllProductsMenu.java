//package ApProject_OnlineShop.view.accountArea.accountAreaForManager;
//
//import ApProject_OnlineShop.controller.MainController;
//import ApProject_OnlineShop.view.LoginRegisterMenu;
//import ApProject_OnlineShop.view.Menu;
//
//public class ManageAllProductsMenu extends Menu {
//    public ManageAllProductsMenu(Menu parentMenu) {
//        super("manage all products", parentMenu);
//        this.submenus.add(new LoginRegisterMenu(this));
//    }
//
//    @Override
//    protected void setCommandNames() {
//        this.commandNames.add("remove");
//    }
//
//    @Override
//    public void execute() {
//        printAllProducts();
//        help();
//        int chosenCommand = getInput();
//        Menu nextMenu = this;
//        if (chosenCommand == 1)
//            nextMenu = submenus.get(0);
//        else if (chosenCommand == 3)
//            nextMenu = getParentMenu();
//        else if (chosenCommand == 2) {
//            removeProduct();
//            nextMenu = this;
//        }
//        nextMenu.execute();
//    }
//
//    private void printAllProducts() {
//        for (String good : MainController.getInstance().getAccountAreaForManagerController().getAllGoodsInfo()) {
//            System.out.println(good);
//        }
//        System.out.println();
//    }
//
//    private void removeProduct() {
//        System.out.println("enter product id that you want remove: ");
//        String productID = getValidInput("\\d+{1,15}", "invalid id format");
//        try {
//            MainController.getInstance().getAccountAreaForManagerController().removeProduct(productID);
//            System.out.println("product removed successfully.");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
