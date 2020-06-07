package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.userExceptions.PasswordIncorrectException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends FxmlController {
    @FXML
    PasswordField password;
    @FXML
    TextField username;


    public void loginButtonPressed(ActionEvent actionEvent) {
        try {
            MainController.getInstance().getLoginRegisterController().loginUser(username.getText(),password.getText());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        } catch (PasswordIncorrectException e) {
            e.printStackTrace();
        }
        //setScene();             go to account area page
    }

    public void goToRegisterMenu(ActionEvent actionEvent) {
        setScene("register.fxml","Register");
    }
}
