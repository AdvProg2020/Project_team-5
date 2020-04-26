package view.accountArea.AccountAreaForSeller;

import controller.MainController;
import exception.ProductNotFoundException;
import view.Menu;

public class ViewOffsMenu extends Menu {
    public ViewOffsMenu(Menu parentMenu) {
        super("view offs", parentMenu);
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 4)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                viewOff();
            if (chosenCommand == 2)
                editOff();
            if (chosenCommand == 3)
                addOff();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    @Override
    public void setCommandNames() {
        commandNames.add("- view off by ID");
        commandNames.add("- edit off");
        commandNames.add("- add off");
    }

    private void viewOff() {
        try {
            String offDetails = MainController.getInstance().getAccountAreaForSellerController().
                    viewOff(Long.parseLong(getValidInput("[0-9]+", "Enter off ID")));
            System.out.println(offDetails);
        }catch (ProductNotFoundException exception){
            System.out.println(exception);
        }
    }

    private void editOff() {

    }

    private void addOff() {

    }
}
