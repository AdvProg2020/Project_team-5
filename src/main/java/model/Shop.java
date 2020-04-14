package model;

public class Shop {
    private static Shop ourInstance = new Shop();

    public static Shop getInstance() {
        return ourInstance;
    }

    private Shop() {

    }
}
