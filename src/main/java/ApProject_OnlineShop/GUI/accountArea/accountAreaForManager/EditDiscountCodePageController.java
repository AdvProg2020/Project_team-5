package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDiscountCodePageController extends FxmlController implements Initializable {
    @FXML
    private TextField discountPercentTextField;
    @FXML
    private TextField discountAmountTextField;
    @FXML
    private TextField codeTextField;
    @FXML
    private DatePicker startDateChooser;
    @FXML
    private DatePicker endDateChooser;
    @FXML
    private TextField customerToAddField;
    @FXML
    private TextField numberOfUseField;
    @FXML
    private TextField customerToRemove;

    public void onBackButtonPressed() {
        setScene("viewDiscountCodesPage.fxml", "manage discount codes");
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onEditDiscountCodePressed() {

    }

    public void onAddCustomerButtonPressed() {

    }

    public void onRemoveButtonPressed() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
