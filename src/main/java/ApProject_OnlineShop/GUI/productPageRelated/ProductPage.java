package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPage extends FxmlController implements Initializable {
    public static long productId = 0;
    public GridPane information;
    public ImageView image;

    public void backButton(ActionEvent actionEvent) {
    }


    public void logout(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void setProductId(long productId) {
        ProductPage.productId = productId;
    }
}
