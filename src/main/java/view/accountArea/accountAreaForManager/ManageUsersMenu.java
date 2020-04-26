package view.accountArea.accountAreaForManager;

import view.Menu;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("manage users", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("- view");
        this.commandNames.add("- delete user");
        this.commandNames.add("- create manager profile");
    }

    @Override
    public void execute() {
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

    private void viewUser() {

    }

    private void deleteUser() {

    }

    private void createManagerProfile() {

    }
}
