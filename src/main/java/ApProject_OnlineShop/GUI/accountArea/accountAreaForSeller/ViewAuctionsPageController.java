package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.RequestForServer;
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

    private String selectedAuctionId;

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
        List<String> allAuctionsId = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getSellerAllAuctionsId", getToken(), null)));
        List<String> allAuctionsTitle = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getSellerAllAuctionsTitle", getToken(), null)));
        int i = 0;
        for (String auction : allAuctionsId) {
            Hyperlink hyperlink = new Hyperlink(auction + "- " + allAuctionsTitle.get(i));
            i++;
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
        int size = allAuctionsId.size() * 50;
        if (size > 422) {
            allAuctionsVBox.setPrefHeight(size);
        }
    }

    private void viewSingleAuction(String auction) {
        singleAuctionVBox.getChildren().clear();
        this.selectedAuctionId = auction;
        Label title = new Label(auction);
        title.setFont(Font.font(16));
        title.setAlignment(Pos.CENTER);
        singleAuctionVBox.getChildren().add(title);
        Label details = new Label("- Details: ");
        details.setFont(Font.font(14));
        details.setAlignment(Pos.CENTER);
        singleAuctionVBox.getChildren().add(details);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(auction);
        List<String> detailes = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getAuctionProperties", getToken(), inputs)));
        for (String detail : detailes) {
            Label label = new Label(detail);
            label.setFont(Font.font(13));
            label.setAlignment(Pos.CENTER);
            singleAuctionVBox.getChildren().add(label);
        }
    }

    public void onRemoveAuctionPressed(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "remove", "Remove Auction", "are you sure to remove this auction?");
        if (result.get() == ButtonType.OK) {
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(selectedAuctionId);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "removeAuction", getToken(), inputs));
            if (serverResponse.equals("auction successfully removed")) {
                SuccessPageFxController.showPage("auction removed successfully", "your auction successfully removed.");
                resetPage();
            } else {
                ErrorPageFxController.showPage("auction cannot be removed", serverResponse);
            }
        } else actionEvent.consume();
    }

    private void resetPage() {
        this.selectedAuctionId = "";
        singleAuctionVBox.getChildren().clear();
        removeButton.setDisable(true);
        endButton.setDisable(true);
        updateAllAuctionsBos();
    }

    public void onEndAuctionPressed() {

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
