package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchasePageController1 extends FxmlController {
    @FXML
    public TextField nameField;
    @FXML
    public TextField postalCodeField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField discountCodeField;


    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onPurchase1(ActionEvent actionEvent) {

    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("cart.fxml","cart");
    }
}
