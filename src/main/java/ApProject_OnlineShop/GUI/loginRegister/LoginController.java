package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.persons.Supporter;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class LoginController extends FxmlController {
    @FXML
    PasswordField password;
    @FXML
    TextField username;
    private static String pathBack;
    private static String titleBack;
    private static String pathAfterLogin = null;
    private static String titleNextPage;

    public void loginButtonPressed(ActionEvent actionEvent) {
//        try {
//            MainController.getInstance().getLoginRegisterController().loginUser(username.getText(), password.getText());
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(username.getText());
        inputs.add(password.getText());
        RequestForServer requestForServer = new RequestForServer("LoginRegisterController", "loginUser", null, inputs);
        String serverResponse = connectToServer(requestForServer);
        if (serverResponse.startsWith("successfully login")) {
            SuccessPageFxController.showPage("Login successful", "you logined successful");
            setToken(serverResponse.split("#")[1]);
            if (pathAfterLogin != null) {
                if (pathAfterLogin.equals("purchasePage1.fxml"))
                    if (!(getCurrentPerson() instanceof Customer)) {
                        ErrorPageFxController.showPage("can not purchase", "you can not purchase because you aren't customer");
                        setScene("mainMenuLayout.fxml", "main menu");
                        return;
                    }
                setScene(pathAfterLogin, pathBack);
                pathAfterLogin = null;
            } else if (getCurrentPerson() instanceof Customer) {
                AccountAreaForCustomerController.setPathBack(pathBack, titleBack);
                setScene("accountAreaForCustomer.fxml", "Account area for customer");
            } else if (getCurrentPerson() instanceof Manager) {
                AccountAreaForManagerFxController.setPathBack(pathBack, titleBack);
                setScene("accountAreaForManager.fxml", "Account area for manager");
            } else if (getCurrentPerson() instanceof Seller) {
                AccountAreaForSellerController.setPathBack(pathBack, titleBack);
                setScene("accountAreaForSeller.fxml", "Account area for seller");
            } else if(getCurrentPerson() instanceof Supporter){
                setScene("accountAreaForSupporter.fxml", "Account area for supporter");
            }
        } else {
            ErrorPageFxController.showPage("Error happened", serverResponse);
            username.clear();
            password.clear();
        }
//        } catch (UsernameNotFoundException | PasswordIncorrectException e) {
//            ErrorPageFxController.showPage("Error happened", e.getMessage());
//            username.clear();
//            password.clear();
//        }
    }

    public void goToRegisterMenu(ActionEvent actionEvent) {
        String choices[] = {"Seller", "Customer"};
        ChoiceDialog dialog = new ChoiceDialog(choices[0], choices);
        dialog.setHeaderText("choice role");
        dialog.setContentText("choice your role that you want to register!");
        dialog.showAndWait();
        if (dialog.getSelectedItem().equals("Seller") && (!dialog.resultProperty().getValue().equals(null))) {
            setScene("registerSeller.fxml", "Register");
        } else if (dialog.getSelectedItem().equals("Customer") && (!dialog.resultProperty().getValue().equals(null))) {
            setScene("registerCustomer.fxml", "Register");
        }
    }

    public void backButtonAction(ActionEvent actionEvent) {
        setScene(pathBack, titleBack);
    }

    public static void setPathBack(String pathBack, String titleBack) {
        LoginController.pathBack = pathBack;
        LoginController.titleBack = titleBack;
    }

    public static void setPathAfterLogin(String pathAfterLogin, String titleAfter) {
        LoginController.pathAfterLogin = pathAfterLogin;
        LoginController.titleNextPage = titleAfter;
    }
}
