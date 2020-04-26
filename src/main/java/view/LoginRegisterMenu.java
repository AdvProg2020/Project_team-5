package view;

import controller.MainController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String input = getInputInFormatWithError("(?i)create account\\s+(manager|customer|seller)\\s+good\\s+(\\w+)","not valid format");
        Matcher matcher = Pattern.compile("(?i)create account\\s+(manager|customer|seller)\\s+good\\s+(\\w+)").matcher(input);
        try {
            MainController.getInstance().getLoginRegisterController().createAccount(matcher.group(1),matcher.group(2));
        }catch (Exception e){

        }
    }

    private void loginUser() {
    }

    private void logout() {
    }

    private static String getInputInFormatWithError(String regex, String error) {
        Pattern pattern = Pattern.compile(regex);
        boolean inputIsInvalid;
        String line;
        do {
            line = scanner.nextLine().trim();
            Matcher matcher = pattern.matcher(line);
            inputIsInvalid = !matcher.find();
            if (inputIsInvalid)
                System.out.print(error);
        } while (inputIsInvalid);
        return line;
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
