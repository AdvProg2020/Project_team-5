package ApProject_OnlineShop;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//import javafx.fxml.FXMLLoader;


public class Main extends Application {
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
//        MainMenu mainMenu = new MainMenu();
//        mainMenu.execute();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new StageController();
       // SuccessPageFxController.showPage("new successful", "hey there this is a success page");
    }
}
