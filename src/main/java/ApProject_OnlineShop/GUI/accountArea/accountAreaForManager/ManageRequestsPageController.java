package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageRequestsPageController extends FxmlController implements Initializable {
    @FXML
    private TableView<DiscountCode> requestsTable;
    @FXML
    private TableColumn<DiscountCode, String> requestColumn;
    @FXML
    private TableColumn<DiscountCode, LocalDate> idColumn;
    @FXML
    private Button acceptButton;
    @FXML
    private Button declineButton;

    private ObservableList<DiscountCode> discountData;
    private ObservableList<String> codeData;
    private long selectedRequest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onAcceptRequestPressed(ActionEvent actionEvent) {
    }

    public void onDeclineRequestPressed(ActionEvent actionEvent) {
    }
}
