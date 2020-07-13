package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
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
}
