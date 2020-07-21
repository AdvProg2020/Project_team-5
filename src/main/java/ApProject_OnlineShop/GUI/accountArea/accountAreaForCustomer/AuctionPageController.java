package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label bestPrice;

    public TextField messageTextField;

    private static String selectedAuctionId;

    private AuctionPageChatThread auctionPageChatThread;

    private AuctionPageBestPriceThread auctionPageBestPriceThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewSingleAuction();
        updateLastPriceLabel();
        updateBestPrice();
        updateChatBox();
        auctionPageChatThread = new AuctionPageChatThread(this, selectedAuctionId);
        auctionPageBestPriceThread = new AuctionPageBestPriceThread(this, selectedAuctionId);
        new Thread(auctionPageBestPriceThread).start();
        new Thread(auctionPageChatThread).start();
        if (!isPersonOfferedPriceInAuction())
            SuccessPageFxController.showPage("offer price", "please first offer a price to participate in auction and chat with other participants");
    }

    @FXML
    public void updateBestPrice() {
        ArrayList<String> input = new ArrayList<>();
        input.add(selectedAuctionId);
        String bestPrice = connectToServer(new RequestForServer("AuctionsController", "getBestPriceOfAuction", getToken(), input));
        this.bestPrice.setText(bestPrice);
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
        if (offeredPriceTextField.getText().isEmpty() || !offeredPriceTextField.getText().matches("\\d+")) {
            ErrorPageFxController.showPage("error in offer price", "invalid format for offered price.");
            updateLastPriceLabel();
            updateChatBox();
            offeredPriceTextField.clear();
            return;
        }
        String offeredPrice = offeredPriceTextField.getText();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(selectedAuctionId);
        inputs.add(offeredPrice);
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForCustomerController", "offerNewPrice", getToken(), inputs));
        if (serverResponse.equals("your price offered successfully")) {
            SuccessPageFxController.showPage("price offer", "your price successfully offered.");
//            setScene("auctionPage.fxml", "Auction Page");
            updateChatBox();
            updateLastPriceLabel();
        } else {
            ErrorPageFxController.showPage("price cannot be offered", serverResponse);
        }
        offeredPriceTextField.clear();
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            //MainController.getInstance().getLoginRegisterController().logoutUser();
            //Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), getInputsForServer()));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    private boolean isPersonOfferedPriceInAuction() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(selectedAuctionId);
        String serverResponse = connectToServer(new RequestForServer("AuctionsController", "isCustomerOfferedAPriceInAuction", getToken(), inputs));
        return serverResponse.equals("true");
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        auctionPageChatThread.setExit(true);
        auctionPageBestPriceThread.setExit(true);
        setScene("viewAllAuctionsForCustomerPage.fxml", "view auctions");
    }

    @FXML
    public void updateChatBox() {
        if (isPersonOfferedPriceInAuction()) {
            chatBox.getChildren().clear();
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(selectedAuctionId);
            ArrayList<Massage> massages = new Gson().fromJson(connectToServer(new RequestForServer("AuctionsController", "getMassages", getToken(), inputs)), new TypeToken<ArrayList<Massage>>() {
            }.getType());
            for (Massage massage : massages) {
                HBox hBox = new HBox();
                if (massage.getSenderUserName().equals(getCurrentPerson().getUsername()))
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                else
                    hBox.setAlignment(Pos.CENTER_LEFT);
                VBox massageBox = getMassageVBox(massage);
                VBox.setMargin(hBox, new Insets(5, 15, 5, 10));
                hBox.getChildren().add(massageBox);
                chatBox.getChildren().add(hBox);
            }
            chatBox.getChildren().add(getAnswerBox());
            scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        }
    }

    public VBox getMassageVBox(Massage massage) {
        VBox vBox = new VBox();
        vBox.setMaxWidth(240);
        vBox.setMinWidth(240);
        vBox.setStyle("-fx-border-color:#8600b3; -fx-border-width: 1;-fx-border-style: solid; -fx-border-radius: 10px;");
        vBox.setAlignment(Pos.CENTER_LEFT);
        Label senderName = new Label(massage.getSenderUserName());
        VBox.setMargin(senderName, new Insets(6, 0, 0, 10));
        senderName.setFont(Font.font("Times New Roman", 15));
        senderName.setTextFill(Color.BLUE);
        vBox.getChildren().add(senderName);
        Label body = new Label(massage.getMassage());
        VBox.setMargin(body, new Insets(6, 0, 0, 10));
        body.setFont(Font.font("Times New Roman", 13));
        vBox.getChildren().add(body);
        String time = "" + massage.getTime();
        Label timeText = new Label(time.substring(0, 2) + ":" + time.substring(3, 5));
        HBox timeHBox = new HBox();
        timeText.setFont(Font.font("Times New Roman", 13));
        timeText.setPadding(new Insets(7, 10, 5, 15));
        timeHBox.setAlignment(Pos.CENTER_RIGHT);
        timeHBox.getChildren().add(timeText);
        vBox.getChildren().add(timeHBox);
        return vBox;
    }

    public HBox getAnswerBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(40);
        hBox.setPrefWidth(442);
        this.messageTextField = new TextField();
        this.messageTextField.setPromptText("type your massage");
        this.messageTextField.setFont(Font.font(14));
        this.messageTextField.setPrefWidth(388);
        this.messageTextField.setMinHeight(30);
        this.messageTextField.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(messageTextField);
        HBox.setMargin(messageTextField, new Insets(7, 0, 1, 7));
        ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/sendIcon.png").toString()));
        imageView.setCursor(Cursor.HAND);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked(e -> sendMassage());
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        HBox.setMargin(imageView, new Insets(0, 3, 0, 7));
        hBox.getChildren().add(imageView);
        return hBox;
    }

    private void sendMassage() {
        Massage massage = new Massage(getCurrentPerson().getUsername(), "auction_" + selectedAuctionId, messageTextField.getText());
        ArrayList<String> input = new ArrayList<>();
        input.add(new Gson().toJson(massage));
        connectToServer(new RequestForServer("AccountAreaController", "sendMassage", getToken(), input));
        updateChatBox();
        messageTextField.clear();
    }
}
