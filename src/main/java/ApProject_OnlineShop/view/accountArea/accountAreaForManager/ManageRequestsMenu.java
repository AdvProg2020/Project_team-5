//package ApProject_OnlineShop.view.accountArea.accountAreaForManager;
//
//import ApProject_OnlineShop.controller.MainController;
//import ApProject_OnlineShop.view.LoginRegisterMenu;
//import ApProject_OnlineShop.view.Menu;
//
//public class ManageRequestsMenu extends Menu {
//    public ManageRequestsMenu(Menu parentMenu) {
//        super("manage requests", parentMenu);
//        this.submenus.add(new LoginRegisterMenu(this));
//    }
//
//    @Override
//    protected void setCommandNames() {
//        this.commandNames.add("show request");
//        this.commandNames.add("accept request");
//        this.commandNames.add("decline request");
//    }
//
//    @Override
//    public void execute() {
//        printAllRequests();
//        help();
//        int chosenCommand = getInput();
//        Menu nextMenu;
//        if (chosenCommand == 1)
//            nextMenu = submenus.get(0);
//        else if (chosenCommand == 5)
//            nextMenu = getParentMenu();
//        else {
//            if (chosenCommand == 2)
//                showDetails();
//            if (chosenCommand == 3)
//                acceptRequest();
//            if (chosenCommand == 4)
//                declineRequest();
//            nextMenu = this;
//        }
//        nextMenu.execute();
//    }
//
//    private void printAllRequests() {
//        for (String request : MainController.getInstance().getAccountAreaForManagerController().getAllRequestsInfo()) {
//            System.out.println(request);
//        }
//    }
//
//    private void showDetails() {
//        System.out.println("enter request id that you want ApProject_OnlineShop.view its details: ");
//        String requestId = getValidInput("\\d+", "invalid id format.");
//        try {
//            System.out.println(MainController.getInstance().getAccountAreaForManagerController().viewRequestDetails(requestId));
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    private void acceptRequest() {
//        System.out.println("enter request id that you want to accept:");
//        String requestId = getValidInput("\\d+", "invalid id format.");
//        try {
//            MainController.getInstance().getAccountAreaForManagerController().acceptRequest(requestId);
//            System.out.println("request accepted.");
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    private void declineRequest() {
//        System.out.println("enter request id that you want to decline:");
//        String requestId = getValidInput("\\d+", "invalid id format.");
//        try {
//            MainController.getInstance().getAccountAreaForManagerController().declineRequest(requestId);
//            System.out.println("request declined.");
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//}
