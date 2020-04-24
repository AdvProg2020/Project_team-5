package view;

import controller.MainController;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    protected ArrayList<Menu> submenus;
    protected Menu parentMenu;
    public static Scanner scanner;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Menu> getSubmenus() {
        return submenus;
    }

    public void setSubmenus(ArrayList<Menu> submenus) {
        this.submenus = submenus;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }


    public  void help(){
        ScreenClearing.clearScreen();
        System.out.println(this.getName() + ":");
        int i = 1;
        for (Menu submenu : this.getSubmenus()) {
            System.out.println("" + (i++) + "-" + submenu.getName());
        }
        if (MainController.getInstance().getCurrentPerson() == null) {
            System.out.println("" + (i++) + "- Login or Register");
        } else {
            System.out.println("" + (i++) + "-Logout");
        }
        if (this.parentMenu != null)
            System.out.println((i++) + "-Back");
        else
            System.out.println((i++) + "-Exit");
    }

    public  void execute(){
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == submenus.size() + 2) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                nextMenu = this.parentMenu;
        } else
            nextMenu = submenus.get(chosenMenu-1);
        nextMenu.help();
        nextMenu.execute();
    }

}
