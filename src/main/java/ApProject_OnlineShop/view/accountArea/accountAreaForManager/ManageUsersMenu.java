package ApProject_OnlineShop.view.accountArea.accountAreaForManager;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.view.LoginRegisterMenu;
import ApProject_OnlineShop.view.Menu;

import java.util.ArrayList;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("manage users", parentMenu);
        this.submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("ApProject_OnlineShop/view");
        this.commandNames.add("delete user");
        this.commandNames.add("create manager profile");
    }

    @Override
    public void execute() {
        showAllUsers();
        help();
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 1)
            nextMenu = submenus.get(0);
        else if (chosenCommand == 5)
            nextMenu = getParentMenu();
        else {
            if (chosenCommand == 2)
                viewUser();
            if (chosenCommand == 3)
                deleteUser();
            if (chosenCommand == 4)
                createManagerProfile();
            nextMenu = this;
        }
        nextMenu.execute();
    }

    private void showAllUsers() {
        System.out.println("all users :");
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
        System.out.print("enter username that you want remove: ");
        String username = getValidInput("\\w+", "invalid username format");
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeUser(username);
            System.out.println("user removed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createManagerProfile() {
        System.out.print("enter new manager's username: ");
        String username = getValidInput("\\w+", "invalid username format");
        ArrayList<String> details = new ArrayList<>();
        System.out.println("enter first name:");
        details.add(getValidInput("[a-zA-Z]{2,}", "not valid format for first name"));
        System.out.println("enter last name:");
        details.add(getValidInput("[a-zA-Z]{2,}", "not valid format for last name"));
        System.out.println("enter email:");
        details.add(getValidInput("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", "not valid format for email"));
        System.out.println("enter phone number:");
        details.add(getValidInput("^\\d{11}$", "not valid format for phone number"));
        System.out.println("enter password:\n" +
                "-must contains one digit from 0-9\n" +
                "-must contains one lowercase characters\n" +
                "-must contains one uppercase characters\n" +
                "-length at least 4 characters and maximum of 16");
        details.add(getValidInput("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})", "not valid format for password"));
        try {
            MainController.getInstance().getAccountAreaForManagerController().createManagerAccount(username, details);
            System.out.println("new manager created successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
