package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.Main;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantBeEditedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDiscountCodePageController extends FxmlController implements Initializable {
    @FXML
    private TextField discountPercentTextField;
    @FXML
    private TextField discountAmountTextField;
    @FXML
    private DatePicker startDateChooser;
    @FXML
    private VBox customersVBox;
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
        String amount = discountAmountTextField.getText();
        String percent = discountPercentTextField.getText();
        LocalDate startDate = startDateChooser.getValue();
        LocalDate endDate = endDateChooser.getValue();
        if (amount.isEmpty() && percent.isEmpty() && startDate == null && endDate == null) {
            ErrorPageFxController.showPage("edit discount code error", "please fill at least a field for edit discount");
            return;
        }
        try {
            if (!percent.isEmpty()) {
                MainController.getInstance().getAccountAreaForManagerController().editDiscountCode(currentDiscount.getCode(), "discountPercent", percent);
            }
            if (!amount.isEmpty()) {
                MainController.getInstance().getAccountAreaForManagerController().editDiscountCode(currentDiscount.getCode(), "maxDiscountAmount", amount);
            }
            if (startDate != null) {
                MainController.getInstance().getAccountAreaForManagerController().editDiscountCode(currentDiscount.getCode(), "startDate", startDate.toString());
            }
            if (endDate != null) {
                MainController.getInstance().getAccountAreaForManagerController().editDiscountCode(currentDiscount.getCode(), "endDate", endDate.toString());
            }
            updateFields();
            SuccessPageFxController.showPage("edit", "discount code edited successfully");
        } catch (Exception e) {
            ErrorPageFxController.showPage("error", e.getMessage());
        }
        clearFields();
    }

    private void clearFields() {
        discountPercentTextField.clear();
        discountAmountTextField.clear();
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
            updateCustomers();
        }
    }

    private void clearAddCustomerFields() {
        customerToAddField.clear();
        numberOfUseField.clear();
    }

    public void onRemoveButtonPressed() {
        String customer = customerToRemove.getText();
        if (customer.isEmpty() || !customer.matches("\\w+")) {
            ErrorPageFxController.showPage("add customer error", "invalid input format");
            customerToRemove.clear();
            return;
        }
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeCustomerFromDiscount(currentDiscount.getCode(), customer);
            SuccessPageFxController.showPage("successful adding customer", "customer removed from code successfully");
        } catch (Exception e) {
            ErrorPageFxController.showPage("error", e.getMessage());
        } finally {
            customerToRemove.clear();
            updateCustomers();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateFields();
        updateCustomers();
    }

    private void updateCustomers() {
        for (Customer customer : currentDiscount.getIncludedCustomers().keySet()) {
            Label label = new Label();
            label.setText(customer.getUsername());
            label.setFont(new Font("Times New Roman",16));
            label.setAlignment(Pos.CENTER);
            customersVBox.getChildren().add(label);
        }
    }

    private void updateFields() {
        startDateChooser.setPromptText(currentDiscount.getStartDate().toString());
        endDateChooser.setPromptText(currentDiscount.getEndDate().toString());
        discountAmountTextField.setPromptText("" + currentDiscount.getMaxDiscountAmount());
        discountPercentTextField.setPromptText("" + currentDiscount.getDiscountPercent());
    }
}
