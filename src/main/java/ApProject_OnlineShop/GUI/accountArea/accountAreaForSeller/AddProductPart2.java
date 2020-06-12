package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductPart2 extends FxmlController implements Initializable {

    private static ArrayList<String> productDetails;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onAddProduct(ActionEvent actionEvent) {
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
    }

    public static ArrayList<String> getProductDetails() {
        return productDetails;
    }

    public static void setProductDetails(ArrayList<String> productDetails) {
        AddProductPart2.productDetails = productDetails;
    }
}
