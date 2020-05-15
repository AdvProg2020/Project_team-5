import model.database.Database;
import view.MainMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Database.getInstance().initializeShop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenu mainMenu = new MainMenu();
        mainMenu.execute();
    }
}
