package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu parentMenu) {
        super("manage requests", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("- show");
        this.commandNames.add("- accept");
        this.commandNames.add("- decline");
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

    }

    private void acceptRequest() {

    }

    private void declineRequest() {

    }
}
