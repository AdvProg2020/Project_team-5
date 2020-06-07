package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlController {

    public void setScene(String addres, String title) {
        Stage stage = StageController.getStage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(addres));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 1000));
       // stage.setMaximized(true);
        stage.show();
    }

}
