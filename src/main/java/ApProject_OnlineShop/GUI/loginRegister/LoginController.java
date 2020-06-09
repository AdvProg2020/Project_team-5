package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
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


    public void loginButtonPressed(ActionEvent actionEvent) {
        try {
            MainController.getInstance().getLoginRegisterController().loginUser(username.getText(), password.getText());
            SuccessPageFxController.showPage("Login successful", "you logined successful");
            if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
                setScene("accountAreaForCustomer.fxml", "Account area for custmoer");
            } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
                setScene("accountAreaForManager.fxml", "Account area for manager");
            } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
                setScene("accountAreaForSeller.fxml", "Account area for seller");
            }
        } catch (UsernameNotFoundException | PasswordIncorrectException e) {
            ErrorPageFxController.showPage("Error happened", e.getMessage());
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
        setScene("mainMenuLayout.fxml", "Main menu");
    }
}
