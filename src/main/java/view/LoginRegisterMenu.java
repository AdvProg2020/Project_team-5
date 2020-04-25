package view;

import controller.MainController;

public class LoginRegisterMenu extends Menu {
    public LoginRegisterMenu(Menu parentMenu) {
        super("Login,Register,Logout", parentMenu);
    }

    public LoginRegisterMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    @Override
    protected void setCommandNames() {
        if (MainController.getInstance().getCurrentPerson() == null) {
            this.commandNames.add("-Register");
            this.commandNames.add("-Login");
        } else {
            this.commandNames.add("-Logout");
        }
    }

    private void registerUser() {
        System.out.println("create account [type] [username]");

    }

    private void loginUser() {
    }

    private void logout() {
    }


    @Override
    public void execute() {
        Menu nextMenu = null;
        int chosenMenu = getInput();
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
