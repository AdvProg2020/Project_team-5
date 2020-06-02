package ApProject_OnlineShop.view.productsPage;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.view.LoginRegisterMenu;
import ApProject_OnlineShop.view.Menu;

import java.util.regex.Pattern;

public class SortingMenu extends Menu {
    public SortingMenu(Menu parentMenu) {
        super("sorting products Menu", parentMenu);
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("show available sorts");
        commandNames.add("sort an available sort");
        commandNames.add("current sort");
        commandNames.add("disable sort");
    }

    @Override
    public void execute() {
        help();
        int chosenCommand = getInput();
        Menu nextMenu = this;
        if (chosenCommand == 1)
            nextMenu = submenus.get(0);
        if (chosenCommand == 2)
            showAvailableSorts();
        if (chosenCommand == 3)
            sortAnAvailableSort();
        if (chosenCommand == 4)
            currentSort();
        if (chosenCommand == 5)
            disableSort();
        if (chosenCommand == 6) {
            nextMenu = this.parentMenu;
        }
        nextMenu.execute();
    }

    private void showAvailableSorts(){
        System.out.println("1-visit number \n2-average rate \n3-date");
    }

    private void sortAnAvailableSort(){
        showAvailableSorts();
        System.out.println("choose an available sort:");
        String chosenSort = scanner.nextLine();
        if (Pattern.matches("^[1-3]$", chosenSort)){
            MainController.getInstance().getControllerForSorting().sortASort(Integer.parseInt(chosenSort));
            return;
        }
        System.out.println("not valid input");
    }

    private void currentSort(){
        System.out.println("current sort: " + MainController.getInstance().getControllerForSorting().getCurrentSort());
    }

    private void disableSort(){
        MainController.getInstance().getControllerForSorting().disableSort();
    }
}
