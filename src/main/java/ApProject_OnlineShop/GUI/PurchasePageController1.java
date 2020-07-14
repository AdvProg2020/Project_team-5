package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchasePageController1 extends FxmlController implements Initializable {
    @FXML
    public TextField nameField;
    @FXML
    public TextField postalCodeField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField phoneNumberField;

    private static ArrayList<String> userInfo = new ArrayList<>();

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onPurchase1(ActionEvent actionEvent) {
        if (checkBaseInfos()) {
            userInfo.clear();
            userInfo.add(nameField.getText());
            userInfo.add(postalCodeField.getText());
            userInfo.add(addressField.getText());
            userInfo.add(phoneNumberField.getText());
            setScene("purchasePage2.fxml", "purchase part2");
        }
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("cart.fxml", "cart");
    }

    public boolean checkBaseInfos() {
        if (!nameField.getText().matches("[a-zA-Z\\s]+")) {
            ErrorPageFxController.showPage("Error for purchase", "name is invalid!");
            return false;
        } else if (!postalCodeField.getText().matches("[\\d]+")) {
            ErrorPageFxController.showPage("Error for purchase", "postal code is invalid!");
            return false;
        } else if (!phoneNumberField.getText().matches("[\\d]{11}")) {
            ErrorPageFxController.showPage("Error for purchase", "phone number is invalid!");
            return false;
        }
        return true;
    }

    public static ArrayList<String> getUserInfo() {
        return userInfo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo.clear();
    }
}
