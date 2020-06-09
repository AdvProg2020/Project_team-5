package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.GUI.loginRegister.AccountAreaForCustomerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StageController {
    private static Stage stage;

    public StageController() {
        this.stage = new Stage();
//        Parent root = null;
//        try {
//            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("accountAreaForCustomer.fxml")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        stage.setTitle("Shop");
//        stage.setScene(new Scene(root, 1000, 800));
//        stage.setMaximized(true);
//        stage.show();
        new AccountAreaForCustomerController().viewDiscountCode(null);

    }

    public static void setSceneJavaFx(GridPane root) {
        stage.setScene(new Scene(root, 1000, 800));
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
