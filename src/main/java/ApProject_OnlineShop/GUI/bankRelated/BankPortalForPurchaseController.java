package ApProject_OnlineShop.GUI.bankRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
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
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void purchaseByBankPortal(ActionEvent actionEvent) throws Exception {
        if (!getCurrentPerson().getUsername().equals(user.getText())){
            ErrorPageFxController.showPage("can not increase credit", "your shop username does not match bank username");
        }else {
            MainController.getInstance().getAccountAreaForCustomerController().purchaseByBankPortal(user.getText(), password.getText(), "" + totalPrice, usedDiscountCode, customerInfo);
            //if clause for proper success and error
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
