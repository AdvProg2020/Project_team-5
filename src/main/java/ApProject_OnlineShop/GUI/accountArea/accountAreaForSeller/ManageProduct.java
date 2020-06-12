package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageProduct extends FxmlController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void backButton(ActionEvent actionEvent) {
    }

    public void addProduct(MouseEvent mouseEvent) {
        setScene("addProductForSeller.fxml","add product");
    }

    public void logout(MouseEvent mouseEvent) {
    }
}
