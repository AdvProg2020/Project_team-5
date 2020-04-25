package view;

import controller.MainController;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Menu {
    private String name;
    protected ArrayList<Menu> submenus;
    protected Menu parentMenu;
    public static Scanner scanner;
    protected ArrayList<String> commandNames;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        this.submenus = new ArrayList<>();
        this.commandNames = new ArrayList<>();
        setCommandNames();
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

    protected abstract void setCommandNames();

    protected String getInput(){
        String input;
        while (true){
            input = scanner.nextLine();
            if(Pattern.matches("[0-9]+",input))
                if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= commandNames.size() + submenus.size() +1)
                    return input;
            System.out.println("not valid input. please try again");
        }
    }

    public void help() {
        ScreenClearing.clearScreen();
        int i = 1;
        if (!submenus.isEmpty())
            System.out.println("Menus of " + this.getName() + ":");
        for (Menu submenu : submenus) {
            if (submenu instanceof LoginRegisterMenu) {
                if (MainController.getInstance().getCurrentPerson() == null) {
                    System.out.println("" + (i++) + "-Login or Register");
                } else {
                    System.out.println("" + (i++) + "-Logout");
                }
            } else
                System.out.println("" + (i++) + "-" + submenu.getName());
        }
        if (!commandNames.isEmpty())
            System.out.println("Commands of " + this.getName() + ":");
        for (String command : commandNames) {
            System.out.println("" + (i++) + command);
        }
        if (this.parentMenu != null)
            System.out.println((i) + "-Back");
        else
            System.out.println((i) + "-Exit");
    }

    public abstract void execute();

}
