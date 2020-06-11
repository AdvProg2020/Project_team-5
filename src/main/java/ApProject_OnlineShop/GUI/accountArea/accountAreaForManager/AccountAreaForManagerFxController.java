package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
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
        setScene("CreatingDiscountCodePage.fxml", "creating discount code");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForManagerController().getUserPersonalInfo();
        userName.setText(personalInfo.get(0));
        name.setText(personalInfo.get(1));
        lastName.setText(personalInfo.get(2));
        email.setText(personalInfo.get(3));
        phoneNumber.setText(personalInfo.get(4));
    }


    public void editField(ActionEvent actionEvent) {
        setScene("editFieldPersons.fxml", "edit field");
    }
}
