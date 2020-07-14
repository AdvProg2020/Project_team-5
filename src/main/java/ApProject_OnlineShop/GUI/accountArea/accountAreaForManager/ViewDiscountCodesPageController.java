package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.RequestForServer;
import com.gilecode.yagson.com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

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
        ArrayList<DiscountCode> allDiscounts = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "getAllDiscountCodes", null, null)), new TypeToken<ArrayList<DiscountCode>>() {
        }.getType());
        updateTableView(allDiscounts);
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
//            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onRowSelected() {
        this.selectedDiscount = discountTable.getSelectionModel().getSelectedItem().getCode();
        codeLabel.setText(discountTable.getSelectionModel().getSelectedItem().getCode());
        startDateLabel.setText(discountTable.getSelectionModel().getSelectedItem().getStartDate().toString());
        endDateLabel.setText(discountTable.getSelectionModel().getSelectedItem().getEndDate().toString());
        amountLabel.setText(discountTable.getSelectionModel().getSelectedItem().getMaxDiscountAmount().toString());
        percentLabel.setText("" + discountTable.getSelectionModel().getSelectedItem().getDiscountPercent());
        editButton.setDisable(false);
        removeButton.setDisable(false);
    }

    public void onEditDiscountPressed() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(selectedDiscount);
        DiscountCode discountCode = new com.google.gson.Gson().fromJson(connectToServer(new RequestForServer("Shop", "findDiscountCode", null, inputs)), DiscountCode.class);
        EditDiscountCodePageController.setCurrentDiscount(discountCode);
        setScene("editDiscountPage.fxml", "edit discount");
    }

    public void onRemovePressed(ActionEvent e) {
        Optional<ButtonType> result = new FxmlController().showAlert
                (Alert.AlertType.CONFIRMATION, "delete", "Discount Delete", "are you sure to delete this discount?");
        if (result.get() == ButtonType.OK) {
//            try {
//                MainController.getInstance().getAccountAreaForManagerController().removeDiscountCode(this.selectedDiscount);
//                this.selectedDiscount = "";
//                updateTableView(Shop.getInstance().getAllDiscountCodes());
//                editButton.setDisable(true);
//                removeButton.setDisable(true);
//                clearLabels();
//                SuccessPageFxController.showPage("delete discount", "discount code deleted successfully");
//            } catch (Exception ex) {
//                ErrorPageFxController.showPage("error", ex.getMessage());
//            }
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(this.selectedDiscount);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "removeDiscountCode", getToken(), inputs));
            if (serverResponse.equals("discountCode removed successfully")) {
                this.selectedDiscount = "";
                ArrayList<DiscountCode> allDiscounts = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "getAllDiscountCodes", null, null)), new TypeToken<ArrayList<DiscountCode>>() {
                }.getType());
                updateTableView(allDiscounts);
                editButton.setDisable(true);
                removeButton.setDisable(true);
                clearLabels();
                SuccessPageFxController.showPage("delete discount", "discount code deleted successfully");
            } else {
                ErrorPageFxController.showPage("error", serverResponse);
            }
        } else
            e.consume();
    }

    private void updateTableView(List<DiscountCode> discounts) {
        discountData = FXCollections.observableArrayList();
        codeData = FXCollections.observableArrayList();
        discountTable.getItems().clear();
        for (DiscountCode discountCode : discounts) {
            discountTable.getItems().add(discountCode);
        }
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        if (discounts.size() * 27 > 400) {
            discountTable.setPrefHeight(discounts.size() * 27);
        }
    }

    public void onEndDateSort() {
        ArrayList<DiscountCode> allDiscounts = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "getAllDiscountCodes", null, null)), new TypeToken<ArrayList<DiscountCode>>() {
        }.getType());
        List<DiscountCode> discountCodes = allDiscounts;
        discountCodes.sort((d1, d2) -> {
            if (d1.getEndDate().isBefore(d2.getEndDate()))
                return 1;
            else if (d1.getEndDate().isAfter(d2.getEndDate()))
                return -1;
            else
                return 0;
        });
        updateTableView(discountCodes);
    }

    public void onPercentSort() {
        ArrayList<DiscountCode> allDiscounts = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "getAllDiscountCodes", null, null)), new TypeToken<ArrayList<DiscountCode>>() {
        }.getType());
        List<DiscountCode> discountCodes = allDiscounts;
        discountCodes.sort((d1, d2) -> (int) (d1.getMaxDiscountAmount() - d2.getMaxDiscountAmount()));
        updateTableView(discountCodes);
    }

    public void onMaxAmountSort() {
        ArrayList<DiscountCode> allDiscounts = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "getAllDiscountCodes", null, null)), new TypeToken<ArrayList<DiscountCode>>() {
        }.getType());
        List<DiscountCode> discountCodes = allDiscounts;
        discountCodes.sort(Comparator.comparingInt(DiscountCode::getDiscountPercent));
        updateTableView(discountCodes);
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
