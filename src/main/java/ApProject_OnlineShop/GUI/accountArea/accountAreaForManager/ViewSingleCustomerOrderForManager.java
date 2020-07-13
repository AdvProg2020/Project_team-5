package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewSingleCustomerOrderForManager extends FxmlController implements Initializable {
    private static Long Id;
    public VBox vBox;
    public Label orderId;
    public Label date;
    public Label goods;
    public Label discountAmount;
    public Label address;
    public Label orderStatus;
    public Label totalPrice;

    public void backButton() {
        setScene("viewCustomerOrdersForManager.fxml", "view customers' orders");
    }

    public void logout() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void changeOrderStatusToReadyToSend() {
        ArrayList<String> input = new ArrayList<>();
        input.add("" + Id);
        input.add("READYTOSEND");
        String response = connectToServer(new RequestForServer("AccountAreaForManagerController", "changeOrderStatus", getToken(), input));
        setScene("showSingleCustomerOrderForManager.fxml", "view single order");
    }

    public void changeOrderStatusToSent() {
        ArrayList<String> input = new ArrayList<>();
        input.add("" + Id);
        input.add("SENT");
        String response = connectToServer(new RequestForServer("AccountAreaForManagerController", "changeOrderStatus", getToken(), input));
        setScene("showSingleCustomerOrderForManager.fxml", "view single order");
    }

    public void changeOrderStatusReceived() {
        ArrayList<String> input = new ArrayList<>();
        input.add("" + Id);
        input.add("RECEIVED");
        String response = connectToServer(new RequestForServer("AccountAreaForManagerController", "changeOrderStatus", getToken(), input));
        setScene("showSingleCustomerOrderForManager.fxml", "view single order");
    }

    public static void setOrderId(Long id) {
        Id = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + Id);
        ArrayList<String> offDetails = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForManagerController", "viewOrderGUI", getToken(), inputs)));
        orderId.setText(offDetails.get(0));
        date.setText(offDetails.get(1));
        goods.setText(offDetails.get(2));
        discountAmount.setText(offDetails.get(3));
        totalPrice.setText(offDetails.get(4));
        address.setText(offDetails.get(5));
        orderStatus.setText(offDetails.get(6));
    }
}
