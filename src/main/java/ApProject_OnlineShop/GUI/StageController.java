package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.GUI.productPageRelated.ProductPage;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class StageController {
    private static Stage stage;

    public StageController() {
        StageController.stage = new Stage();
        Shop.getInstance().addGoodToCart(Shop.getInstance().findGoodById(1L),(Seller) Shop.getInstance().findUser("yasaman"),2);
        Shop.getInstance().addGoodToCart(Shop.getInstance().findGoodById(2L),(Seller) Shop.getInstance().findUser("yasaman"),2);
        Shop.getInstance().addGoodToCart(Shop.getInstance().findGoodById(16L),(Seller) Shop.getInstance().findUser("jafar"),4);
        Shop.getInstance().addGoodToCart(Shop.getInstance().findGoodById(6L),(Seller) Shop.getInstance().findUser("yasaman"),2);
        Shop.getInstance().addGoodToCart(Shop.getInstance().findGoodById(7L),(Seller) Shop.getInstance().findUser("yasaman"),2);
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
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("mainMenuLayout.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Shop");
        stage.setScene(new Scene(root, 1000, 800));
        stage.getIcons().add(new Image(getClass().getClassLoader().getResource("pictures/iconcopy.png").toString()));
        //       stage.setMaximized(true);
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
