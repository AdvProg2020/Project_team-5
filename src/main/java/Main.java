import exception.FileCantBeDeletedException;
import exception.FileCantBeSavedException;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//import javafx.fxml.FXMLLoader;
import view.MainMenu;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
//        try {
//            Database.getInstance().initializeShop();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (FileCantBeSavedException e) {
//            e.printStackTrace();
//        } catch (FileCantBeDeletedException e) {
//            e.printStackTrace();
//        }
//        MainMenu mainMenu = new MainMenu();
//        mainMenu.execute();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/mainMenu/test.fxml"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }
}
