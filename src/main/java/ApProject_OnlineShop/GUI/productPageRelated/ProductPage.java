package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ProductPage extends FxmlController implements Initializable {
    public static long productId = 0;
    public ImageView image;
    public Label name;
    public Label brand;
    public Label category;
    public Label subcategory;
    public HBox rate;
    public Label views;
    public Label details;
    public VBox sellers;
    public Label price;
    public VBox properties;
    public ScrollPane comments;

    public void backButton(ActionEvent actionEvent) {
    }


    public void logout(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.setImage(new Image(Paths.get("Resources/productImages/"+ productId+".jpg").toUri().toString()));

    }

    public static void setProductId(long productId) {
        ProductPage.productId = productId;
    }
}
