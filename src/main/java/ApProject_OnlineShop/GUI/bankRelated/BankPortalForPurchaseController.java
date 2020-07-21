package ApProject_OnlineShop.GUI.bankRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BankPortalForPurchaseController extends FxmlController implements Initializable {
    private static long totalPrice;
    private static ArrayList<String> customerInfo;
    private static String usedDiscountCode;
    public TextField user;
    public PasswordField password;
    public Label finalPrice;

    public void onBackButtonPressedCustomer(ActionEvent actionEvent) {
        setScene("accountAreaForCustomer.fxml", "account area");
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), getInputsForServer()));
            ArrayList<String> inputs11 = new ArrayList<>();
            inputs11.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs11));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void purchaseByBankPortal(ActionEvent actionEvent) throws Exception {
        if (!getCurrentPerson().getUsername().equals(user.getText())) {
            ErrorPageFxController.showPage("can not increase credit", "your shop username does not match bank username");
        } else {
//            MainController.getInstance().getAccountAreaForCustomerController().purchaseByBankPortal(user.getText(), password.getText(), "" + totalPrice, usedDiscountCode, customerInfo);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(user.getText());
            inputs.add(password.getText());
            inputs.add("" + totalPrice);
            inputs.add(usedDiscountCode);
            inputs.add(getId() + "");
            inputs.addAll(customerInfo);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForCustomerController", "purchaseByBankPortal", getToken(), inputs));
            if (serverResponse.equals("purchase was successful")) {
                SuccessPageFxController.showPage("purchase", "purchase was successful");
                ArrayList<String> inputs00 = new ArrayList<>();
                inputs00.add(getId() + "");
                connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs00));
                FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            } else {
                ErrorPageFxController.showPage("error", serverResponse);
            }
        }
        setScene("accountAreaForCustomer.fxml", "account area for customer");
    }

    public static void setTotalPrice(long totalPrice) {
        BankPortalForPurchaseController.totalPrice = totalPrice;
    }

    public static void setCustomerInfo(ArrayList<String> customerInfo) {
        BankPortalForPurchaseController.customerInfo = customerInfo;
    }

    public static void setUsedDiscountCode(String usedDiscountCode) {
        BankPortalForPurchaseController.usedDiscountCode = usedDiscountCode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        finalPrice.setText(totalPrice + " Rials");
    }
}
