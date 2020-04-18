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

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }


    public abstract void help();

    public abstract void execute();

}
