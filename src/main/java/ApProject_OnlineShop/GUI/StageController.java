package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.productThings.GoodInCart;
import ApProject_OnlineShop.server.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StageController extends FxmlController {
    private static Stage stage;

    public StageController() {
        StageController.stage = new Stage();
        FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
        StageController.stage.setOnCloseRequest(e -> {
            Optional<ButtonType> result = new FxmlController().showAlert
                    (Alert.AlertType.CONFIRMATION, "Exit", "Exit", "are you sure to exit shop?");
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            } else
                e.consume();
        });
        Parent root = null;
        try {
            ArrayList<Person> allPersons = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "getAllPersons", null, null)), new TypeToken<ArrayList<Person>>() {
            }.getType());
            if (allPersons.size() == 0) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("registerManager.fxml")));
            } else
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("mainMenuLayout.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Shop");
        stage.setScene(new Scene(root, 1000, 800));
        stage.getIcons().add(new Image(getClass().getClassLoader().getResource("pictures/iconcopy.png").toString()));
        stage.show();
    }

    public static void setSceneJavaFx(GridPane root) {
        stage.setScene(new Scene(root, 1000, 800));
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
