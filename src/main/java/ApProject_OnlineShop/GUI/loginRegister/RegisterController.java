package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.userExceptions.MainManagerAlreadyRegistered;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterController extends FxmlController {
    @FXML
    PasswordField password;
    @FXML
    TextField username, email, phoneNumber, credit, firstName, lastName;


    public void RegisterPressed(ActionEvent actionEvent) {
        if (!username.getText().matches("\\w+")) {

        } else if (!firstName.getText().matches("[a-zA-Z]{2,}")) {

        } else if (!lastName.getText().matches("[a-zA-Z]{2,}")) {

        } else if (!email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}$")) {

        } else if (!phoneNumber.getText().matches("^\\d{11}$")) {

        } else if (!password.getText().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})")) {

        } else if (!credit.getText().matches("\\d\\d\\d\\d+")) {

        } else {
            ArrayList<String> details = new ArrayList<>();
            details.add(firstName.getText());
            details.add(lastName.getText());
            details.add(email.getText());
            details.add(phoneNumber.getText());
            details.add(password.getText());
            details.add(credit.getText());
            try {
                MainController.getInstance().getLoginRegisterController().createAccount("customer", username.getText(), details);
            } catch (UsernameIsTakenAlreadyException e) {

            } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {

            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileCantBeSavedException e) {
                e.printStackTrace();
            }
        }
    }
}
