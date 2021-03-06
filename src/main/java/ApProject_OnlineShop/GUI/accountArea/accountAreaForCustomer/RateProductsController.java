package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.productPageRelated.ProductBriefSummery;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RateProductsController extends FxmlController implements Initializable {
    @FXML
    public GridPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> productIdsString = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getBoughtProducts", getToken(), null)));
        List<Long> productIds = new ArrayList<>();
        for (String s : productIdsString) {
            productIds.add(Long.parseLong(s));
        }
//        List<Long> productIds = MainController.getInstance().getAccountAreaForCustomerController().getBoughtProducts();
        int num = 0;
        int row = 0;
        if (productIds.size() % 3 == 0) {
            if (((productIds.size() / 3) + 0) * 250 > 577) {
                root.setPrefHeight(((productIds.size() / 3) + 0) * 250);
            }
        } else {
            if (((productIds.size() / 3) + 1) * 250 > 577) {
                root.setPrefHeight(((productIds.size() / 3) + 1) * 250);
            }
        }
        for (Long productId : productIds) {
            VBox vbox;
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(productId + "");
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "isInOff", getToken(), inputs));
            if (serverResponse.equals("true")) {
                vbox = new ProductBriefSummery().offProductBriefSummery(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> rateProduct(productId));
            }
            if (serverResponse.equals("false")) {
                vbox = new ProductBriefSummery().getProductForAllProductsPage(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> rateProduct(productId));
            }
            if (num % 3 == 0)
                row++;
        }
    }

    private void rateProduct(long productId2) {
        RateProductsPart2Controller.setProductIdForRate(productId2);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Rate");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(SuccessPageFxController.class.getClassLoader().getResource("ratePage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 400);
        window.setScene(scene);
        RateProductsPart2Controller.setWindow(window);
        window.showAndWait();
    }

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

}
