package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
        viewSingleAuction();
        updateLastPriceLabel();
    }

    private void viewSingleAuction() {
        singleAuctionVBox.getChildren().clear();
        Label title = new Label(selectedAuctionId);
        title.setFont(Font.font(16));
        title.setAlignment(Pos.CENTER);
        singleAuctionVBox.getChildren().add(title);
        Label details = new Label("- Details: ");
        details.setFont(Font.font(14));
        details.setAlignment(Pos.CENTER);
        singleAuctionVBox.getChildren().add(details);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(selectedAuctionId);
        List<String> detailes = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getAuctionProperties", getToken(), inputs)));
        for (String detail : detailes) {
            Label label = new Label(detail);
            label.setFont(Font.font(13));
            label.setAlignment(Pos.CENTER);
            singleAuctionVBox.getChildren().add(label);
        }
    }

    private void updateLastPriceLabel() {
        ArrayList<String> input = new ArrayList<>();
        input.add(selectedAuctionId);
        String lastPrice = connectToServer(new RequestForServer("AccountAreaForCustomerController", "getLastOfferedPriceOfCustomer", getToken(), input));
        lastPriceLabel.setText(lastPrice);
    }

    public static void setSelectedAuctionId(String selectedAuctionId) {
        AuctionPageController.selectedAuctionId = selectedAuctionId;
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
