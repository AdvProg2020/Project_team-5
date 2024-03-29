//package ApProject_OnlineShop.view;
//
//import ApProject_OnlineShop.controller.MainController;
//import ApProject_OnlineShop.model.Shop;
//import ApProject_OnlineShop.model.persons.Customer;
//import ApProject_OnlineShop.model.persons.Manager;
//import ApProject_OnlineShop.model.persons.Seller;
//import ApProject_OnlineShop.view.accountArea.accountAreaForSeller.AccountAreaForSeller;
//import ApProject_OnlineShop.view.accountArea.accountAreaForManager.AccountAreaForManager;
//import ApProject_OnlineShop.view.accountArea.acountAreaForCustomer.AccountAreaForCustomer;
//
//import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class LoginRegisterMenu extends Menu {
//    public LoginRegisterMenu(Menu parentMenu) {
//        super("Login,Register,Logout", parentMenu);
//    }
//
//    public LoginRegisterMenu(String name, Menu parentMenu) {
//        super(name, parentMenu);
//    }
//
//    @Override
//    protected void setCommandNames() {
//        commandNames.clear();
//        if (MainController.getInstance().getCurrentPerson() == null) {
//            this.commandNames.add("Register");
//            this.commandNames.add("Login");
//        } else {
//            this.commandNames.add("Logout");
//        }
//    }
//
//    private Menu registerUser() {
//        System.out.println("create account [type] [username]");
//        String input = getValidInput("(?i)create account\\s+(manager|customer|seller)\\s+(\\w+)", "not valid format");
//        Matcher matcher = Pattern.compile("(?i)create account\\s+(manager|customer|seller)\\s+(\\w+)").matcher(input);
//        ArrayList<String> details = new ArrayList<>();
//        System.out.println("enter first name");
//        details.add(getValidInput("[a-zA-Z]{2,}", "not valid format for first name"));
//        System.out.println("enter last name");
//        details.add(getValidInput("[a-zA-Z]{2,}", "not valid format for last name"));
//        System.out.println("enter email");
//        details.add(getValidInput("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", "not valid format for email"));
//        System.out.println("enter phone number");
//        details.add(getValidInput("^\\d{11}$", "not valid format for phone number"));
//        System.out.println("enter password\n" +
//                "-must contains one digit from 0-9\n" +
//                "-must contains one lowercase characters\n" +
//                "-must contains one uppercase characters\n" +
//                "-length at least 4 characters and maximum of 16");
//        details.add(getValidInput("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})", "not valid format for password"));
//        matcher.find();
//        if (matcher.group(1).equals("customer")) {
//            System.out.println("enter your credit");
//            details.add(getValidInput("\\d\\d\\d\\d+", "not valid format"));
//        }
//        if (matcher.group(1).equals("seller")) {
//            System.out.println("now you must enter your company details");
//            System.out.println("enter company name");
//            details.add(getValidInput("[a-zA-Z]{1,}", "not valid format for name"));
//            System.out.println("enter company's website");
//            details.add(getValidInput("^(https?:\\/\\/)?(www\\.)?([a-zA-Z0-9]+(-?[a-zA-Z0-9])*\\.)+[\\w]{2,}(\\/\\S*)?$",
//                    "not valid format for website"));
//            System.out.println("enter company's phone number");
//            details.add(getValidInput("^\\d{11}$", "not valid format for phone number"));
//            System.out.println("enter company's fax number");
//            details.add(getValidInput("^\\d+{6,}$", "not valid format for fax number"));
//            System.out.println("enter company's address");
//            details.add(scanner.nextLine());
//        }
//        try {
//            MainController.getInstance().getLoginRegisterController().createAccount(matcher.group(1), matcher.group(2), details);
//            if (matcher.group(1).equals("seller"))
//                System.out.println("your request sent to manager for being registered.");
//            else
//                System.out.println("registered successful");
//        } catch (
//                Exception e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("enter any key to continue");
//        scanner.nextLine();
//        return this;
//    }
//
//    private Menu loginUser() {
//        Menu nextMenu;
//        System.out.println("enter username");
//        String username = scanner.nextLine();
//        System.out.println("enter password");
//        String password = scanner.nextLine();
//        try {
//            MainController.getInstance().getLoginRegisterController().loginUser(username, password);
//            System.out.println("login successful");
//            nextMenu = this.setNextMenu();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            nextMenu = this;
//        }
//        System.out.println("press any key to continue");
//        scanner.nextLine();
//        return nextMenu;
//    }
//
//    private Menu setNextMenu() {
//        Menu nextMenu;
//        if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
//            this.getMainMenu(this).setSubMenu(0, new AccountAreaForManager(this.getParentMenu()));
//        } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
//            this.getMainMenu(this).setSubMenu(0, new AccountAreaForCustomer(this.getParentMenu()));
//        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
//            this.getMainMenu(this).setSubMenu(0, new AccountAreaForSeller(this.getParentMenu()));
//        }
//        if (this.getName().equals("Account area")) {
//            nextMenu = this.getParentMenu().getSubmenus().get(0);
//        } else {
//            nextMenu = this.getParentMenu();
//        }
//        return nextMenu;
//    }
//
//    private Menu logout() {
//        Menu mainMenu = getMainMenu(this);
//        Menu nextMenu;
//        mainMenu.setSubMenu(0, new LoginRegisterMenu("Account area", mainMenu));
//        if (!this.getParentMenu().equals(mainMenu)) {
//            if (this.getParentMenu().getParentMenu().getName().startsWith("Account area for ") ||
//                    this.getParentMenu().getName().startsWith("Account area for ")) {
//                nextMenu = mainMenu;
//            } else
//                nextMenu = this.getParentMenu();
//        } else
//            nextMenu = mainMenu;
//        MainController.getInstance().getLoginRegisterController().logoutUser();
//        Shop.getInstance().clearCart();
//        return nextMenu;
//    }
//
//    private Menu getMainMenu(Menu menu) {
//        if (menu.getParentMenu() == null)
//            return menu;
//        return getMainMenu(menu.getParentMenu());
//    }
//
//    @Override
//    public void execute() {
//        setCommandNames();
//        help();
//        Menu nextMenu = null;
//        int chosenMenu = getInput();
//        if (MainController.getInstance().getCurrentPerson() == null) {
//            if (chosenMenu == 1) {
//                nextMenu = registerUser();
//            } else if (chosenMenu == 2) {
//                nextMenu = loginUser();
//            } else if (chosenMenu == 3) {
//                nextMenu = this.parentMenu;
//            }
//        } else {
//            if (chosenMenu == 1) {
//                nextMenu = logout();
//            } else if (chosenMenu == 2) {
//                nextMenu = this.parentMenu;
//            }
//        }
//        nextMenu.execute();
//    }
//}
