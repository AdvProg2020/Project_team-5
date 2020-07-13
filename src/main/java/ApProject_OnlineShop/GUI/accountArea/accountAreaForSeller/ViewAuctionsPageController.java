package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewAuctionsPageController extends FxmlController implements Initializable {
    @FXML
    private VBox allAuctionsVBox;
    @FXML
    private VBox singleAuctionVBox;
    @FXML
    private Button removeButton;
    @FXML
    private Button endButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateAllAuctionsBos();
    }

    private void updateAllAuctionsBos() {
        allAuctionsVBox.getChildren().clear();
        Label label = new Label("All Auctions List");
        label.setFont(Font.font(15));
        label.setAlignment(Pos.CENTER);
        allAuctionsVBox.getChildren().add(label);
        List<String> allAuctions = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getAllAuctionsTitle", getToken(), null)));
        for (String auction : allAuctions) {
            Hyperlink hyperlink = new Hyperlink("- " + auction);
            hyperlink.setOnMouseClicked(e -> {
                viewSingleAuction(auction);
                removeButton.setDisable(false);
                endButton.setDisable(false);
            });
            hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            hyperlink.setAlignment(Pos.BOTTOM_LEFT);
            hyperlink.setPadding(new Insets(8));
            hyperlink.setCursor(Cursor.HAND);
            hyperlink.setUnderline(false);
            hyperlink.setPrefSize(150, 50);
            hyperlink.setFont(new Font(14));
            allAuctionsVBox.getChildren().add(hyperlink);
        }
        int size = allAuctions.size() * 50;
        if (size > 422) {
            allAuctionsVBox.setPrefHeight(size);
        }
    }

    private void viewSingleAuction(String auction) {

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
        setScene("accountAreaForSeller.fxml", "account area");
    }
}
