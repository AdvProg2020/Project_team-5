package view;

import controller.MainController;

public class MainMenu extends Menu{


    public MainMenu() {
        super("Main Menu", null);
    }

    @Override
    public void help() {
        System.out.println(this.getName() + ":");
        int i = 1;
        for (Menu submenu : this.getSubmenus()) {
            System.out.println("" + (i++) + "-" + submenu.getName());
        }
        if (MainController.getInstance().getCurrentPerson() == null) {
            System.out.println("" + (i++) + "- Login or Register");
        } else {
            System.out.println("" + (i++) + "- Logout");
        }
        if (this.parentMenu != null)
            System.out.println((i++) + "= Back");
        else
            System.out.println((i++) + "- Exit");
    }

    @Override
    public void execute(){
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == submenus.size() + 2) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                nextMenu = this.parentMenu;
        } else if (chosenMenu == submenus.size() + 1) {
            if (MainController.getInstance().getCurrentPerson() == null){
                //ToDo
                //go to login register menu
            } else{
                //ToDo
                //logout
            }
        } else
            nextMenu = submenus.get(chosenMenu);
        nextMenu.help();
        nextMenu.execute();
    }
}

