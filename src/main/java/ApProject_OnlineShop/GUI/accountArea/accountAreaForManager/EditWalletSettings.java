package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class EditWalletSettings extends FxmlController {

    public TextField percent;
    public TextField minimumAmount;

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), getInputsForServer()));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
//            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void saveBankingFeePercent() {
        if (!Pattern.matches("[\\d]{1,2}", percent.getText())) {
            ErrorPageFxController.showPage("error happened", "invalid input for percent");
        } else {
            ArrayList<String> input = new ArrayList<>();
            input.add(percent.getText());
            RequestForServer requestForServer = new RequestForServer("AccountAreaForManagerController", "setBankingFeePercent", null, input);
            String response = connectToServer(requestForServer);
            SuccessPageFxController.showPage("success", response);
        }
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void saveMinimumAmount() {
        if (!Pattern.matches("[\\d]+", minimumAmount.getText())) {
            ErrorPageFxController.showPage("error happened", "invalid input for minimum amount");
        } else {
            ArrayList<String> input = new ArrayList<>();
            input.add(minimumAmount.getText());
            RequestForServer requestForServer = new RequestForServer("AccountAreaForManagerController", "setMinimumAmountForWallet", null, input);
            String response = connectToServer(requestForServer);
            SuccessPageFxController.showPage("success", response);
        }
        setScene("accountAreaForManager.fxml", "account area");
    }
}
