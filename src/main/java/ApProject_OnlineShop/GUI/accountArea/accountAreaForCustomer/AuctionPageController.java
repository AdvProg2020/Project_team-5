package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AuctionPageController extends FxmlController implements Initializable {
    @FXML
    private Label lastPriceLabel;
    @FXML
    private TextField offeredPriceTextField;
    @FXML
    private VBox singleAuctionVBox;
    @FXML
    private VBox chatBox;

    private static String selectedAuctionId;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void setSelectedAuctionId(String selectedAuctionId) {
        AuctionPageController.selectedAuctionId = selectedAuctionId;
        updateSingleAuctionBox();
    }

    private static void updateSingleAuctionBox() {
    }

    public void onSubmitPricePressed() {

    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            //MainController.getInstance().getLoginRegisterController().logoutUser();
            //Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }


    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("viewAllAuctionsForCustomerPage.fxml", "view auctions");
    }
}
