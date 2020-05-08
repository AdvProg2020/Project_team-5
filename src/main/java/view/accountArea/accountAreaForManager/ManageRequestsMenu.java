package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu parentMenu) {
        super("manage requests", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("show request");
        this.commandNames.add("accept request");
        this.commandNames.add("decline request");
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 4)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                showDetails();
            if (chosenCommand == 2)
                acceptRequest();
            if (chosenCommand == 3)
                declineRequest();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void printAllRequests() {
        for (String request : MainController.getInstance().getAccountAreaForManagerController().getAllRequestsInfo()) {
            System.out.println(request);
        }
    }

    private void showDetails() {
        System.out.println("enter request id that you want view its details: ");
        String requestId = getValidInput("\\d+", "invalid id format.");
        try {
            System.out.println(MainController.getInstance().getAccountAreaForManagerController().viewRequestDetails(requestId));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void acceptRequest() {
        System.out.println("enter request id that you want to accept:");
        String requestId = getValidInput("\\d+", "invalid id format.");
        try {
            MainController.getInstance().getAccountAreaForManagerController().acceptRequest(requestId);
            System.out.println("request accepted.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void declineRequest() {
        System.out.println("enter request id that you want to decline:");
        String requestId = getValidInput("\\d+", "invalid id format.");
        try {
            MainController.getInstance().getAccountAreaForManagerController().declineRequest(requestId);
            System.out.println("request declined.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
