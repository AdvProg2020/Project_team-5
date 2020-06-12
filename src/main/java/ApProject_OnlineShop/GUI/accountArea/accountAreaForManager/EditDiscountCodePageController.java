package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.Main;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.productThings.DiscountCode;
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

    private static DiscountCode currentDiscount;

    public static void setCurrentDiscount(DiscountCode currentDiscount) {
        EditDiscountCodePageController.currentDiscount = currentDiscount;
    }

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
        String username = customerToAddField.getText();
        String numberOfUse = numberOfUseField.getText();
        if (username.isEmpty() || numberOfUse.isEmpty()) {
            ErrorPageFxController.showPage("add customer error", "please fill both fields for add a customer to discount");
            clearAddCustomerFields();
            return;
        }
        if (!username.matches("\\w+") || !numberOfUse.matches("\\d+")) {
            ErrorPageFxController.showPage("add customer error", "invalid input format");
            clearAddCustomerFields();
            return;
        }
        try {
            MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode(currentDiscount.getCode(), username, numberOfUse);
            SuccessPageFxController.showPage("successful adding customer", "customer added to code successfully");
        } catch (Exception e) {
            ErrorPageFxController.showPage("error", e.getMessage());
        } finally {
            clearAddCustomerFields();
        }
    }

    private void clearAddCustomerFields() {
        customerToAddField.clear();
        numberOfUseField.clear();
    }

    public void onRemoveButtonPressed() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startDateChooser.setPromptText(currentDiscount.getStartDate().toString());
        endDateChooser.setPromptText(currentDiscount.getEndDate().toString());
        discountAmountTextField.setPromptText("" + currentDiscount.getMaxDiscountAmount());
        discountPercentTextField.setPromptText("" + currentDiscount.getDiscountPercent());
    }
}
