package ApProject_OnlineShop.GUI.accountArea.accountAreaForSupporter;

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

public class ViewCustomersChatController extends FxmlController implements Initializable {

    public VBox vBox;

    public void backButton(ActionEvent actionEvent) {
        setScene("accountAreaForSupporter.fxml", "account area");
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
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(getCurrentPerson().getUsername());
        ArrayList<String> customers = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSupporterController", "getCustomersChat", getToken(), inputs)));
        if (customers != null) {
            for (String customer : customers) {
                Hyperlink hyperlink = new Hyperlink("-  " + customer);
                hyperlink.setPrefSize(280, 50);
                hyperlink.setOnMouseClicked(e -> chatPage(customer));
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                hyperlink.setFont(new Font("Times New Roman", 18));
                vBox.getChildren().add(hyperlink);
            }
        }
    }

    public void chatPage(String customer){
        ChatPageController.setOwner(getCurrentPerson().getUsername());
        ChatPageController.setGuest(customer);
        ChatPageController.setPath("viewCustomers'ChatForSupporter.fxml");
        ChatPageController.setBackTitle("chat with customers");
        setScene("chatPage.fxml", "chat Page");
    }

    public void refreshPage(MouseEvent mouseEvent) {
        setScene("viewCustomers'ChatForSupporter.fxml", "chat with customers");
    }
}
