package ApProject_OnlineShop;

import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    public static void main(String[] args) {
     /*   try {
            Database.getInstance().initializeShop();
        } catch (IOException | FileCantBeDeletedException | FileCantBeSavedException e) {
            e.printStackTrace();
        }*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new StageController();
    }
}
