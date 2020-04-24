package view;

import controller.MainController;

public class LoginRegisterMenu extends Menu {
    public LoginRegisterMenu(Menu parentMenu) {
        super("Login,Register,Logout", parentMenu);
    }

    public LoginRegisterMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    private void registerUser() {
        
    }

    private void loginUser() {
    }

    private void logout() {
    }

    @Override
    public void help() {
        ScreenClearing.clearScreen();
        if (MainController.getInstance().getCurrentPerson() == null) {
            System.out.println("1-Register");
            System.out.println("2-Login");
            System.out.println("3-Back");
        } else {
            System.out.println("1-Logout");
            System.out.println("2-Back");
        }
    }

    @Override
    public void execute() {
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (MainController.getInstance().getCurrentPerson() == null) {
            if (chosenMenu == 1) {
                registerUser();
            } else if (chosenMenu == 2) {
                loginUser();
            } else if (chosenMenu == 3) {
                nextMenu = this.parentMenu;
            }
        } else {
            if (chosenMenu == 1) {
                logout();
            } else if (chosenMenu == 2) {
                nextMenu = this.parentMenu;
            }
        }
    }
}
