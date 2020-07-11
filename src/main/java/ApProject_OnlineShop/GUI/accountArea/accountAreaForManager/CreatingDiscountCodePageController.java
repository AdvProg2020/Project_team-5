package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.Main;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantCreatedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreatingDiscountCodePageController extends FxmlController implements Initializable {
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

    private boolean isDiscountCreated = false;
    private String code;

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onCreateDiscountCodePressed() {
        ArrayList<String> discountCodeFields = initializeDiscountFields();
        if (discountCodeFields == null)
            return;
//        try {
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "createNewDiscountCode", getToken(), discountCodeFields));
//            MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(discountCodeFields);
        if (serverResponse.equals("discountCode created successfully")) {
            SuccessPageFxController.showPage("successful discount creation", "discount code created successfully");
            this.isDiscountCreated = true;
        } else {
            ErrorPageFxController.showPage("error", serverResponse);
            clearFields();
        }
//        } catch (Exception e) {
//            ErrorPageFxController.showPage("error", e.getMessage());
//            clearFields();
//        }
    }

    private void clearFields() {
        codeTextField.clear();
        discountPercentTextField.clear();
        discountAmountTextField.clear();
    }

    private ArrayList<String> initializeDiscountFields() {
        String code = codeTextField.getText();
        String amount = discountAmountTextField.getText();
        String percent = discountPercentTextField.getText();
        LocalDate startDate = startDateChooser.getValue();
        LocalDate endDate = endDateChooser.getValue();
        if (code.isEmpty() || amount.isEmpty() || percent.isEmpty() || startDate == null || endDate == null) {
            ErrorPageFxController.showPage("create discount code error", "please fill all fields for create a valid discount");
            clearFields();
            return null;
        }
        if (!code.matches("\\w{1,15}")) {
            ErrorPageFxController.showPage("create discount code error", "invalid discount code format");
            clearFields();
            return null;
        }
        if (!percent.matches("\\d{1,2}")) {
            ErrorPageFxController.showPage("create discount code error", "invalid discount percent format");
            clearFields();
            return null;
        }
        if (!amount.matches("\\d{1,15}")) {
            ErrorPageFxController.showPage("create discount code error", "invalid discount amount format");
            clearFields();
            return null;
        }
        this.code = code;
        return new ArrayList<>(Arrays.asList(code, startDate.toString(), endDate.toString(), amount, percent));
    }

    public void onAddCustomerButtonPressed() {
        String username = customerToAddField.getText();
        String numberOfUse = numberOfUseField.getText();
        if (!isDiscountCreated) {
            ErrorPageFxController.showPage("add customer error", "please create discount first and then add customers to discount");
            clearAddCustomerFields();
            return;
        }
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
//        try {
//            MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode(this.code, username, numberOfUse);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(this.code);
        inputs.add(username);
        inputs.add(numberOfUse);
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "addIncludedCustomerToDiscountCode", getToken(), inputs));
        if (serverResponse.equals("customer included successfully")) {
            SuccessPageFxController.showPage("successful adding customer", "customer added to code successfully");
            clearAddCustomerFields();
        } else {
            ErrorPageFxController.showPage("error", serverResponse);
            clearAddCustomerFields();
        }
//        } catch (Exception e) {
//            ErrorPageFxController.showPage("error", e.getMessage());
//        } finally {
//            clearAddCustomerFields();
//        }
    }

    private void clearAddCustomerFields() {
        customerToAddField.clear();
        numberOfUseField.clear();
    }

    public void onFinishButtonPressed() {
        if (!isDiscountCreated) {
            ErrorPageFxController.showPage("error", "please create discount first and then click finish");
            clearAddCustomerFields();
            return;
        }
        setScene("accountAreaForManager.fxml", "account area");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
