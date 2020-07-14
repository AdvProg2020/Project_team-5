package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatPageController extends FxmlController implements Initializable {
    private static String owner;
    private static String guest;
    private static String path;
    private static String backTitle;
    public TextField massageTextField;
    public VBox vBox;
    public Label title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("chat with " + guest);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(owner);
        inputs.add(guest);
        ArrayList<Massage> massages = new Gson().fromJson(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getMassages", getToken(), inputs)), new TypeToken<ArrayList<Massage>>() {
        }.getType());
        for (Massage massage : massages) {
            HBox hBox = new HBox();
            if (massage.getSenderUserName().equals(owner))
                hBox.setAlignment(Pos.CENTER_RIGHT);
            else
                hBox.setAlignment(Pos.CENTER_LEFT);
            VBox massageBox = getMassageVBox(massage);
            VBox.setMargin(hBox, new Insets(10, 5, 5, 10));
            vBox.getChildren().add(hBox);
        }
        vBox.getChildren().add(getAnswerBox());
    }

    public VBox getMassageVBox(Massage massage) {
        VBox vBox = new VBox();
        vBox.setMaxWidth(240);
        vBox.setMinWidth(240);
        vBox.setStyle("-fx-border-color:#8600b3; -fx-border-width: 1;-fx-border-style: solid;");
        vBox.setAlignment(Pos.CENTER_LEFT);
        Label senderName = new Label(massage.getSenderUserName());
        VBox.setMargin(senderName, new Insets(6, 0, 0, 10));
        senderName.setFont(Font.font("Times New Roman", 15));
        senderName.setStyle("-fx-text-color: # #600080");
        vBox.getChildren().add(senderName);
        Label body = new Label(massage.getMassage());
        VBox.setMargin(body, new Insets(6, 0, 0, 10));
        body.setFont(Font.font("Times New Roman", 13));
        vBox.getChildren().add(body);
        String time = "" + massage.getTime();
        Label timeText = new Label(time.substring(0, 2) + ":" + time.substring(3, 5));
        timeText.setFont(Font.font("Times New Roman", 13));
        timeText.setPadding(new Insets(7, 5, 5, 15));
        vBox.getChildren().add(timeText);
        return vBox;
    }

    public HBox getAnswerBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(40);
        hBox.setPrefWidth(442);
        this.massageTextField = new TextField();
        this.massageTextField.setPromptText("type your massage");
        this.massageTextField.setFont(Font.font(14));
        this.massageTextField.setPrefWidth(388);
        this.massageTextField.setMinHeight(30);
        this.massageTextField.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(massageTextField);
        HBox.setMargin(massageTextField, new Insets(0, 0, 0, 2));
        ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/sendIcon.png").toString()));
        imageView.setCursor(Cursor.HAND);
        imageView.setOnMouseClicked(e -> sendMassage());
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        HBox.setMargin(imageView, new Insets(0, 3, 0, 7));
        hBox.getChildren().add(imageView);
        return hBox;
    }

    private void sendMassage() {
        Massage massage = new Massage(owner, guest, massageTextField.getText());
        ArrayList<String> input = new ArrayList<>();
        input.add(new Gson().toJson(massage));
        connectToServer(new RequestForServer("AccountAreaController", "sendMassage", getToken(), input));
        setScene("chatPage.fxml", "chat page");
    }

    public void backButton(ActionEvent actionEvent) {
        setScene(path, backTitle);
    }

    public void logout(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), getInputsForServer()));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public static void setOwner(String owner) {
        ChatPageController.owner = owner;
    }

    public static void setGuest(String guest) {
        ChatPageController.guest = guest;
    }

    public static void setPath(String path) {
        ChatPageController.path = path;
    }

    public static void setBackTitle(String backTitle) {
        ChatPageController.backTitle = backTitle;
    }
}
