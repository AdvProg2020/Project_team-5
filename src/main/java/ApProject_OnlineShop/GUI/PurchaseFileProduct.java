package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.model.RequestForServer;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.productThings.FileProduct;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchaseFileProduct extends FxmlController implements Initializable {
    private static long fileProductId = 0;

    @FXML
    private TextField discountCode;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Label priceLabel;

    private String code;
    private long finalPrice = -1L;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void setFileProductId(long fileProductId) {
        PurchaseFileProduct.fileProductId = fileProductId;
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
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
        } else mouseEvent.consume();
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("cart.fxml", "cart");
    }

    public void checkDiscountCode(MouseEvent mouseEvent) {
        String discountCode1 = this.discountCode.getText();
        ArrayList<String> inputs1 = new ArrayList<>();
        inputs1.add("" + fileProductId);
        FileProduct fileProduct = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findFileProductById", null, inputs1)), FileProduct.class);
        if (discountCode1.isEmpty()) {
            finalPrice = fileProduct.getPrice();
            priceLabel.setText(fileProduct.getPrice() + " Rials");
        } else {
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(discountCode1);
            inputs.add(getId() + "");
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForCustomerController", "checkValidDiscountCode", getToken(), inputs));
            if (serverResponse.equals("true")) {
                DiscountCode discountCode = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findDiscountCode", null, inputs)), DiscountCode.class);
                this.code = discountCode.getCode();
                if ((fileProduct.getPrice() * discountCode.getDiscountPercent())/100 > discountCode.getMaxDiscountAmount()) {
                    finalPrice = fileProduct.getPrice();
                    priceLabel.setText(finalPrice + "Rials");
                } else {
                    finalPrice = (fileProduct.getPrice() * (100 - discountCode.getDiscountPercent()))/100;
                    priceLabel.setText(finalPrice + "Rials");
                }
            } else {
                ErrorPageFxController.showPage("error for accept discountCode", serverResponse);
                discountCode.clear();
                phoneNumberField.clear();
            }
        }
    }

    public void onPurchase(ActionEvent actionEvent) {
        if (phoneNumberField.getText().isEmpty() || !phoneNumberField.getText().matches("\\d+")) {
            ErrorPageFxController.showPage("error for purchase", "invalid or empty phone number");
            discountCode.clear();
            phoneNumberField.clear();
            return;
        }
        if (this.finalPrice == -1) {
            ErrorPageFxController.showPage("error for purchase", "please first click on discountCode check box.");
            discountCode.clear();
            phoneNumberField.clear();
            return;
        }
        String phoneNumber = phoneNumberField.getText();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(fileProductId + "");
        inputs.add(phoneNumber);
        inputs.add(this.code);
        inputs.add(finalPrice + "");
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForCustomerController", "purchaseFileProductByWallet", getToken(), inputs));
        if (serverResponse.equals("purchase successful")) {

        } else {
            ErrorPageFxController.showPage("error for purchase", serverResponse);
            discountCode.clear();
            phoneNumberField.clear();
        }
    }
}
