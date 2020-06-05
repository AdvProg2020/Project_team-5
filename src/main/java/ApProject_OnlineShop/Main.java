package ApProject_OnlineShop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.fxml.FXMLLoader;


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
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainMenuLayout.fxml"));
        primaryStage.setTitle("Project");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
