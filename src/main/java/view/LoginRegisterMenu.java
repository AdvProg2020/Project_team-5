package view;

import view.accountArea.AccountAreaForCustomer;

public class LoginRegisterMenu extends Menu {
    public LoginRegisterMenu(Menu parentMenu) {
        super("Login,Register,Logout", parentMenu);
    }

    public LoginRegisterMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    @Override
    public void help() {
        if (this.getName().equalsIgnoreCase("Account area")) this.setParentMenu(new AccountAreaForCustomer(this.getParentMenu()));
    }

    @Override
    public void execute() {

    }
}
