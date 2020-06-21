package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.userExceptions.PasswordIncorrectException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        try {
            MainController.getInstance().getLoginRegisterController().loginUser(username.getText(), password.getText());
            SuccessPageFxController.showPage("Login successful", "you logined successful");
            if (pathAfterLogin != null) {
                setScene(pathAfterLogin, pathBack);
                pathAfterLogin = null;
            } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
                AccountAreaForCustomerController.setPathBack(pathBack,titleBack);
                setScene("accountAreaForCustomer.fxml", "Account area for customer");
            } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
                AccountAreaForManagerFxController.setPathBack(pathBack,titleBack);
                setScene("accountAreaForManager.fxml", "Account area for manager");
            } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
                AccountAreaForSellerController.setPathBack(pathBack,titleBack);
                setScene("accountAreaForSeller.fxml", "Account area for seller");
            }
        } catch (UsernameNotFoundException | PasswordIncorrectException e) {
            ErrorPageFxController.showPage("Error happened", e.getMessage());
            username.clear();
            password.clear();
        }
    }

    public void goToRegisterMenu(ActionEvent actionEvent) {
        String choices[] = {"Manager", "Seller", "Customer"};
        ChoiceDialog dialog = new ChoiceDialog(choices[2], choices);
        dialog.setHeaderText("choice role");
        dialog.setContentText("choice your role that you want to register!");
        dialog.showAndWait();
        if (dialog.getSelectedItem().equals("Manager")) {
            setScene("registerManager.fxml", "Register");
        } else if (dialog.getSelectedItem().equals("Seller")) {
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
