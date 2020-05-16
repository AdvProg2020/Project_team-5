import exception.FileCantBeDeletedException;
import exception.FileCantBeSavedException;
import model.database.Database;
import view.MainMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Database.getInstance().initializeShop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        } catch (FileCantBeDeletedException e) {
            e.printStackTrace();
        }
        MainMenu mainMenu = new MainMenu();
        mainMenu.execute();
    }
}
