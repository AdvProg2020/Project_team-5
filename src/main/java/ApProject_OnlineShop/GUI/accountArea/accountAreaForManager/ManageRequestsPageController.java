package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.requests.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        acceptButton.setDisable(false);
        declineButton.setDisable(false);
    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onAcceptRequestPressed(ActionEvent actionEvent) {
    }

    public void onDeclineRequestPressed(ActionEvent actionEvent) {
    }
}
