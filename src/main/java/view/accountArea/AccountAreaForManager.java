package view.accountArea;

import view.Menu;

public class AccountAreaForManager extends Menu {
    public AccountAreaForManager(Menu parentMenu) {
        super("Account area for manager", parentMenu);
    }

    @Override
    protected void setCommandNames() {
        this.commandNames.add("-create discount code");
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        int chosenMenu = getInput();
    }
}
