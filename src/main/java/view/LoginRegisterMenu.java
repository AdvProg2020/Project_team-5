package view;

import controller.MainController;

import java.util.ArrayList;
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
        String input = getValidInput("(?i)create account\\s+(manager|customer|seller)\\s+good\\s+(\\w+)","not valid format");
        Matcher matcher = Pattern.compile("(?i)create account\\s+(manager|customer|seller)\\s+good\\s+(\\w+)").matcher(input);
        ArrayList<String> details=new ArrayList<>();
        System.out.println("enter first name");
        details.add(getValidInput("[a-zA-Z]{2,}","not valid format for first name"));
        System.out.println("enter last name");
        details.add(getValidInput("[a-zA-Z]{2,}","not valid format for last name"));
        System.out.println("enter email");
        details.add(getValidInput("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$","not valid format for email"));
        System.out.println("enter phone number");
        details.add(getValidInput("^\\d{11}$","not valid format for phone number"));
        System.out.println("enter password\n" +
                "-must contains one digit from 0-9\n" +
                "-must contains one lowercase characters\n" +
                "-must contains one uppercase characters\n" +
                "-length at least 4 characters and maximum of 16\n");
        details.add(getValidInput("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})","not valid format for password"));
        System.out.println("enter your credit");
        details.add(getValidInput("\\d\\d\\d\\d+","not valid format"));
        if (matcher.group(1).equals("seller")){
            System.out.println("now you must enter your company details");
            System.out.println("enter company name");
            details.add(getValidInput("[a-zA-Z]{1,}","not valid format for name"));
            System.out.println("enter company's website");
            details.add(getValidInput("^(https?:\\/\\/)?(www\\.)?([a-zA-Z0-9]+(-?[a-zA-Z0-9])*\\.)+[\\w]{2,}(\\/\\S*)?$",
                    "not valid format for website"));
            System.out.println("enter company's phone number");
            details.add(getValidInput("^\\d{11}$","not valid format for phone number"));
            System.out.println("enter company's fax number");
            details.add(getValidInput("^\\d+{6,}$","not valid format for fax number"));
            System.out.println("enter company's address");
            details.add(scanner.nextLine());
        }
        try {
            MainController.getInstance().getLoginRegisterController().createAccount(matcher.group(1),matcher.group(2),details);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("enter any key to continue");
            scanner.nextLine();
        }
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
