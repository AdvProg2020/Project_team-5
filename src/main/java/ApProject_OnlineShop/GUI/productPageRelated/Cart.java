package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class Cart extends FxmlController implements Initializable {
    public VBox items;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setHBoxStyle(HBox hBox) {
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;" +
                "-fx-background-color: linear-gradient(to bottom right, #ffb3ff, #ffffff);");
        hBox.setMinHeight(540);
        hBox.setMinWidth(200);
        hBox.setSpacing(10);
    }
}
