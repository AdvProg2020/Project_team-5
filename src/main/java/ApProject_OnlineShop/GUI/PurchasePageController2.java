package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.GUI.bankRelated.BankPortalForPurchaseController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchasePageController2 extends FxmlController implements Initializable {
    @FXML
    public TextField discountCode;
    @FXML
    public Label totalPrice, label1;
    @FXML
    public Button purchaseButton;
    private String discountCodeString;
    private long totalPrice1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        discountCodeString = null;
        totalPrice1 = 0;
    }

    public void onPurchaseByWallet() {
        try {
            MainController.getInstance().getAccountAreaForCustomerController().purchaseByWallet(totalPrice1, PurchasePageController1.getUserInfo(), discountCodeString);
            SuccessPageFxController.showPage("purchase was successful", totalPrice.getText() + " has reduced from your account!");
            setScene("mainMenuLayout.fxml", "main menu");
        } catch (Exception e) {
            ErrorPageFxController.showPage("error happened during purchase", e.getMessage());
        }

    }

    public void onPurchaseByBankPortal() {
        BankPortalForPurchaseController.setTotalPrice(totalPrice1);
        BankPortalForPurchaseController.setCustomerInfo(PurchasePageController1.getUserInfo());
        BankPortalForPurchaseController.setUsedDiscountCode(discountCodeString);
        setScene("bankPortal.fxml", "bank portal");
    }

    public void checkDiscountCode() {
        String discountCode1 = discountCode.getText();
        if (discountCode1.equals("")) {
            totalPrice1 = MainController.getInstance().getAccountAreaForCustomerController().finalPriceOfAList(Shop.getInstance().getCart());
            discountCodeString = null;
        } else {
//            try {
//                MainController.getInstance().getAccountAreaForCustomerController().checkValidDiscountCode(discountCode1);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(discountCode1);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForCustomerController", "checkValidDiscountCode", getToken(), inputs));
            if (serverResponse.equals("true")) {
                String serverResponse2 = connectToServer(new RequestForServer("AccountAreaForCustomerController", "useDiscountCode", getToken(), inputs));
                if (serverResponse2.equals("this discount code has expired")) {
                    ErrorPageFxController.showPage("error for accept discountCode", serverResponse2);
                    setScene("purchasePage2.fxml", "purchase");
                } else {
                    totalPrice1 = Long.parseLong(serverResponse2);
                }
//                totalPrice1 = MainController.getInstance().getAccountAreaForCustomerController().useDiscountCode(discountCode1);
            } else {
                ErrorPageFxController.showPage("error for accept discountCode", serverResponse);
                setScene("purchasePage2.fxml", "purchase");
            }
//            } catch (Exception exception) {
//                ErrorPageFxController.showPage("error for accept discountCode", exception.getMessage());
//                setScene("purchasePage2.fxml", "purchase");
//            }
            discountCodeString = discountCode1;
        }
        totalPrice.setText(totalPrice1 + " Rials");
        totalPrice.setVisible(true);
        label1.setVisible(true);
        purchaseButton.setVisible(true);
        purchaseButton.setDisable(false);
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

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("purchasePage1.fxml", "purchase");
    }
}
