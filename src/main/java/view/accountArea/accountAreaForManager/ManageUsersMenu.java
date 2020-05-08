package view.accountArea.accountAreaForManager;

import controller.MainController;
import view.Menu;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("manage users", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("view");
        this.commandNames.add("delete user");
        this.commandNames.add("create manager profile");
    }

    @Override
    public void execute() {
        showAllUsers();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 4)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 1)
                viewUser();
            if (chosenCommand == 2)
                deleteUser();
            if (chosenCommand == 3)
                createManagerProfile();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void showAllUsers() {
        for (String person : MainController.getInstance().getAccountAreaForManagerController().getAllUsersList()) {
            System.out.println("# " + person);
        }
    }

    private void viewUser() {
        System.out.print("enter username: ");
        String username = getValidInput("\\w+", "invalid username format");
        try {
            System.out.println(MainController.getInstance().getAccountAreaForManagerController().viewUserInfo(username));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteUser() {

    }

    private void createManagerProfile() {

    }
}
