package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.requests.Request;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Optional;

public class IncreaseCreditController extends FxmlController {
    public TextField user;
    public PasswordField password;
    public TextField money;

    public void onBackButtonPressed() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void increaseCredit() {
        if (!getCurrentPerson().getUsername().equals(user.getText())){
            ErrorPageFxController.showPage("can not increase credit", "your shop username does not match bank username");
        }else {
            ArrayList<String> input = new ArrayList<>();
            input.add(user.getText());
            input.add(password.getText());
            input.add(money.getText());
            RequestForServer requestForServer = new RequestForServer("BankTransactionsController", "increaseCustomerCredit", getToken(), input);
            String response = connectToServer(requestForServer);
            if (response.equals("done successfully"))
                SuccessPageFxController.showPage("successfully done", "credit increased successfully");
            else {
                ErrorPageFxController.showPage("error happened", response);
            }
            setScene("accountAreaForCustomer.fxml", "account area for customer");
        }
    }
}
