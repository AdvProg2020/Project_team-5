package ApProject_OnlineShop.GUI.bankRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Optional;

public class BankRelatedController extends FxmlController {

    public TextField user;
    public PasswordField password;
    public TextField money;

    public void onBackButtonPressedCustomer() {
        setScene("accountAreaForCustomer.fxml", "account area");
    }

    public void onBackButtonPressedSeller(){
        setScene("accountAreaForSeller.fxml", "account area");
    }

    public void onLogoutIconClicked() {
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

    public void increaseCreditCustomer() {
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

    public void withdrawMoneySeller() {
        if (!getCurrentPerson().getUsername().equals(user.getText())){
            ErrorPageFxController.showPage("can not increase credit", "your shop username does not match bank username");
        }else {
            ArrayList<String> input = new ArrayList<>();
            input.add(user.getText());
            input.add(password.getText());
            input.add(money.getText());
            RequestForServer requestForServer = new RequestForServer("BankTransactionsController", "withdrawMoneySeller", getToken(), input);
            String response = connectToServer(requestForServer);
            if (response.equals("done successfully"))
                SuccessPageFxController.showPage("successfully done", money.getText() + "Rials withdrawn successfully");
            else {
                ErrorPageFxController.showPage("error happened", response);
            }
            setScene("accountAreaForSeller.fxml", "account area for customer");
        }
    }

    public void depositMoneySeller(){
        if (!getCurrentPerson().getUsername().equals(user.getText())){
            ErrorPageFxController.showPage("can not increase credit", "your shop username does not match bank username");
        }else {
            ArrayList<String> input = new ArrayList<>();
            input.add(user.getText());
            input.add(password.getText());
            input.add(money.getText());
            RequestForServer requestForServer = new RequestForServer("BankTransactionsController", "depositMoneySeller", getToken(), input);
            String response = connectToServer(requestForServer);
            if (response.equals("done successfully"))
                SuccessPageFxController.showPage("successfully done", money.getText() + "Rials deposited successfully");
            else {
                ErrorPageFxController.showPage("error happened", response);
            }
            setScene("accountAreaForSeller.fxml", "account area for customer");
        }
    }
}
