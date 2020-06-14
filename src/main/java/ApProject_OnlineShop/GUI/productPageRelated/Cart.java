package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class Cart extends FxmlController implements Initializable {
    public VBox items;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Long> productIds = MainController.getInstance().getAccountAreaForCustomerController().viewInCartProducts();
        for (Long productId : productIds) {
            HBox productBox = new HBox();
            setHBoxStyle(productBox);
            ImageView imageView = new ImageView(new Image(Paths.get("Resources/productImages/" + productId + ".jpg").toUri().toString()));
            imageView.setFitWidth(199.5);
            imageView.setFitHeight(199.5);
            productBox.getChildren().add(imageView);
            List<String> goodInfo = MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(productId);
            VBox textFieldVBox = new VBox();
            textFieldVBox.setMaxHeight(200);
            HBox nameHBox = new HBox();
            items.getChildren().add(productBox);
        }
    }
    public void setHBoxStyle(HBox hBox) {
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;" +
                "-fx-background-color: linear-gradient(to bottom right, #ffb3ff, #ffffff);");
        hBox.setMinHeight(200);
        hBox.setMinWidth(540);
        hBox.setMaxWidth(540);
        hBox.setMaxHeight(200);
    }
}
