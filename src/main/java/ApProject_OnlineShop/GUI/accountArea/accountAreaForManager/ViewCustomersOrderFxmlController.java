package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.ViewSpecificOffController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.server.RequestForServer;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCustomersOrderFxmlController extends FxmlController implements Initializable {

    public VBox vBox;

    public void backButton(ActionEvent actionEvent) {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void logout(MouseEvent mouseEvent) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> orders = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForManagerController", "getCustomersOrders", getToken(), null)));
        for (String order : orders) {
            Hyperlink hyperlink = new Hyperlink("- " + order);
            hyperlink.setPrefSize(350, 50);
            hyperlink.setOnMouseClicked(e -> viewSingleOrder(order));
            hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            hyperlink.setAlignment(Pos.BOTTOM_LEFT);
            hyperlink.setPadding(new Insets(8));
            hyperlink.setUnderline(false);
            hyperlink.setFont(new Font(14));
            vBox.getChildren().add(hyperlink);
            int size = orders.size() * 50;
            if (size > 348) {
                vBox.setPrefHeight(size);
            }
        }
    }

    private void viewSingleOrder(String order) {
        long id = Long.parseLong(order.substring("order ID: ".length() , order.indexOf("  ")));
        System.out.println(id);
    }
}