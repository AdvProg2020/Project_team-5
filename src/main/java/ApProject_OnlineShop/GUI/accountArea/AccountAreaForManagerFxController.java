package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountAreaForManagerFxController extends FxmlController implements Initializable {
    @FXML
    private Label userName;
    @FXML
    private Label name;
    @FXML
    private Label lastName;
    @FXML
    private Label email;
    @FXML
    private Label phoneNumber;

    public void onBackButtonPressed() {
        setScene("mainMenuLayout.fxml","main menu");
    }

    public void onLogoutClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onViewDiscountCodesPressed() {

    }

    public void onManageUsersPressed() {

    }

    public void onManageRequestsPressed() {

    }

    public void onManageAllProductsPressed() {

    }

    public void onManageCategoriesPressed() {

    }

    public void onCreateNewDiscountPressed() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
