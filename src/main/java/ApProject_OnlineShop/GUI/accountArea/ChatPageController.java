package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatPageController extends FxmlController implements Initializable {
    private static String owner ;
    private static String guest ;
    private static String path;
    private static String backTitle;
    public VBox vBox;
    public Label title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("chat with " + guest);
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
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
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
