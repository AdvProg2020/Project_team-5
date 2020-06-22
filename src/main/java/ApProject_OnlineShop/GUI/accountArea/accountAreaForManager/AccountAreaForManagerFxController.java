package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.view.accountArea.accountAreaForManager.AccountAreaForManager;
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
    private static String pathBack;
    private static String titleBack;


    public void onBackButtonPressed() {
        setScene(pathBack, titleBack);
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
        setScene("viewDiscountCodesPage.fxml", "manage discount codes");
    }

    public void onManageUsersPressed() {
        setScene("manageAllUsersPage.fxml", "manage users");
    }

    public void onManageRequestsPressed() {
        setScene("manageRequestsPage.fxml", "manage requests");
    }

    public void onManageAllProductsPressed() {
        setScene("manageAllProductsPage.fxml", "manage products");
    }

    public void onManageCategoriesPressed() {
        setScene("manageCategoriesPage.fxml", "manage categories");
    }

    public void onCreateNewDiscountPressed() {
        setScene("CreatingDiscountCodePage.fxml", "creating discount code");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playMusicBackGround(false, false, true);
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

    public static void setPathBack(String pathBack, String titleBack) {
        AccountAreaForManagerFxController.pathBack = pathBack;
        AccountAreaForManagerFxController.titleBack = titleBack;
    }
}
