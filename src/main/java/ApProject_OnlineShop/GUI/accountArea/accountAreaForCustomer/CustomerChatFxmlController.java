package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.ChatPageController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerChatFxmlController extends FxmlController implements Initializable {
    public VBox vBox;

    public void backButton(ActionEvent actionEvent) {
        setScene("accountAreaForCustomer.fxml", "account area");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> onlineSupporters = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getOnlineSupporters", getToken(), null)));
        if (onlineSupporters != null) {
            for (String supporter : onlineSupporters) {
                Hyperlink hyperlink = new Hyperlink("-  " + supporter);
                hyperlink.setPrefSize(280, 50);
                hyperlink.setOnMouseClicked(e -> chatPage(supporter));
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                hyperlink.setFont(new Font("Times New Roman", 18));
                vBox.getChildren().add(hyperlink);
            }
        }
    }

    public void chatPage(String supporterUsername) {
        ChatPageController.setOwner(getCurrentPerson().getUsername());
        ChatPageController.setGuest(supporterUsername);
        ChatPageController.setPath("customerChat.fxml");
        ChatPageController.setBackTitle("customer chat");
        setScene("chatPage.fxml", "chat Page");
    }

    public void refreshPage(MouseEvent mouseEvent) {
        setScene("customerChat.fxml", "customer chat");
    }
}
