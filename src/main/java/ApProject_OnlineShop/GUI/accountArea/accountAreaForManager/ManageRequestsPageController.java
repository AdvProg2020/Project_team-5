package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.RequestNotFoundException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.requests.Request;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageRequestsPageController extends FxmlController implements Initializable {
    @FXML
    private TableView<Request> requestsTable;
    @FXML
    private TableColumn<Request, String> requestColumn;
    @FXML
    private TableColumn<Request, Long> idColumn;
    @FXML
    private Button acceptButton;
    @FXML
    private Button declineButton;
    @FXML
    private Label detailsLabel;

    private ObservableList<DiscountCode> requestsData;
    private ObservableList<Long> idData;
    private long selectedRequest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableView(Shop.getInstance().getAllRequest());
    }

    private void updateTableView(ArrayList<Request> requests) {
        requestsData = FXCollections.observableArrayList();
        idData = FXCollections.observableArrayList();
        requestsTable.getItems().clear();
        for (Request request : requests) {
            requestsTable.getItems().add(request);
        }
        requestColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        if (requests.size() * 27 > 400) {
            requestsTable.setPrefHeight(requests.size() * 27);
        }
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
        this.selectedRequest = requestsTable.getSelectionModel().getSelectedItem().getRequestId();
//        try {
//            detailsLabel.setText(MainController.getInstance().getAccountAreaForManagerController().viewRequestDetails("" + this.selectedRequest));
//        } catch (RequestNotFoundException e) {
//            ErrorPageFxController.showPage("error", e.getMessage());
//        }
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(this.selectedRequest + "");
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "viewRequestDetails", getToken(), inputs));
        if (serverResponse.equals("#error")) {
            ErrorPageFxController.showPage("error", "request not found");
        } else {
            detailsLabel.setText(serverResponse);
        }
        acceptButton.setDisable(false);
        declineButton.setDisable(false);
    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onAcceptRequestPressed(ActionEvent actionEvent) {
//        try {
//            MainController.getInstance().getAccountAreaForManagerController().acceptRequest("" + this.selectedRequest);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + this.selectedRequest);
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "acceptRequest", getToken(), inputs));
        if (serverResponse.equals("request accepted successfully")) {
            this.selectedRequest = 0L;
            updateTableView(Shop.getInstance().getAllRequest());
            acceptButton.setDisable(true);
            declineButton.setDisable(true);
            detailsLabel.setText("");
            SuccessPageFxController.showPage("accept", "request accepted successfully");
        } else {
            ErrorPageFxController.showPage("error", serverResponse);
        }
//        } catch (Exception e) {
//            ErrorPageFxController.showPage("error", e.getMessage());
//        }
    }

    public void onDeclineRequestPressed(ActionEvent actionEvent) {
//        try {
//            MainController.getInstance().getAccountAreaForManagerController().declineRequest("" + this.selectedRequest);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + this.selectedRequest);
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "declineRequest", getToken(), inputs));
        if (serverResponse.equals("request declined successfully")) {
            this.selectedRequest = 0L;
            updateTableView(Shop.getInstance().getAllRequest());
            acceptButton.setDisable(true);
            declineButton.setDisable(true);
            detailsLabel.setText("");
            SuccessPageFxController.showPage("decline", "request declined successfully");
        } else {
            ErrorPageFxController.showPage("error", serverResponse);
        }
//        } catch (Exception e) {
//            ErrorPageFxController.showPage("error", e.getMessage());
//        }
    }
}
