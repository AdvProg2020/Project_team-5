package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewDiscountCodesPageController extends FxmlController implements Initializable {
    @FXML
    private TableView<DiscountCode> discountTable;
    @FXML
    private TableColumn<DiscountCode, String> codeColumn;
    @FXML
    private TableColumn<DiscountCode, LocalDate> startColumn;
    @FXML
    private Label codeLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label percentLabel;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;

    private ObservableList<DiscountCode> discountData;
    private ObservableList<String> codeData;
    private String selectedDiscount;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableView();
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onRowSelected() {
        this.selectedDiscount = discountTable.getSelectionModel().getSelectedItem().getCode();
        codeLabel.setText(discountTable.getSelectionModel().getSelectedItem().getCode());
        startDateLabel.setText(discountTable.getSelectionModel().getSelectedItem().getStartDate().toString());
        endDateLabel.setText(discountTable.getSelectionModel().getSelectedItem().getEndDate().toString());
        amountLabel.setText(discountTable.getSelectionModel().getSelectedItem().getMaxDiscountAmount().toString());
        percentLabel.setText("" +discountTable.getSelectionModel().getSelectedItem().getDiscountPercent());
        editButton.setDisable(false);
        removeButton.setDisable(false);
    }

    public void onEditDiscountPressed() {
        setScene("editDiscountPage.fxml", "edit discount");
    }

    public void onRemovePressed(ActionEvent e) {
        Optional<ButtonType> result = new FxmlController().showAlert
                (Alert.AlertType.CONFIRMATION, "delete", "Discount Delete", "are you sure to delete this discount?");
        if (result.get() == ButtonType.OK) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeDiscountCode(this.selectedDiscount);
                this.selectedDiscount = "";
                updateTableView();
                editButton.setDisable(true);
                removeButton.setDisable(true);
                clearLabels();
                SuccessPageFxController.showPage("delete discount", "discount code deleted successfully");
            } catch (Exception ex) {
                ex.printStackTrace();
                ErrorPageFxController.showPage("error", ex.getMessage());
            }
        } else
            e.consume();
    }

    private void updateTableView() {
        discountData = FXCollections.observableArrayList();
        codeData = FXCollections.observableArrayList();
        discountTable.getItems().clear();
        for (DiscountCode discountCode : Shop.getInstance().getAllDiscountCodes()) {
            discountTable.getItems().add(discountCode);
        }
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    }

    private void clearLabels() {
        codeLabel.setText("");
        startDateLabel.setText("");
        endDateLabel.setText("");
        amountLabel.setText("");
        percentLabel.setText("");
    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }
}
