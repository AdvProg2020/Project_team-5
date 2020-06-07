package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.userExceptions.MainManagerAlreadyRegistered;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterController extends FxmlController {
    @FXML
    Label passwordLabel;
    @FXML
    PasswordField password;
    @FXML
    TextField username, email, phoneNumber, firstName, lastName;
    @FXML
    TextField credit;


    public void RegisterForCustomerPressed(ActionEvent actionEvent) {
        if (checkBaseInfos()) {
            if (!credit.getText().matches("\\d\\d\\d\\d+")) {

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

    public void RegisterForManagerPressed(ActionEvent actionEvent) {
        if (checkBaseInfos()) {
            ArrayList<String> details = new ArrayList<>();
            details.add(firstName.getText());
            details.add(lastName.getText());
            details.add(email.getText());
            details.add(phoneNumber.getText());
            details.add(password.getText());
            try {
                MainController.getInstance().getLoginRegisterController().createAccount("manager", username.getText(), details);
            } catch (UsernameIsTakenAlreadyException e) {

            } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {

            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileCantBeSavedException e) {
                e.printStackTrace();
            }
        }
    }

    public void RegisterForSellerPressed(ActionEvent actionEvent) {
    }

    public boolean checkBaseInfos() {
        if (!username.getText().matches("\\w+")) {
            return false;
        } else if (!firstName.getText().matches("[a-zA-Z]{2,}")) {
            return false;
        } else if (!lastName.getText().matches("[a-zA-Z]{2,}")) {
            return false;
        } else if (!email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}$")) {
            return false;
        } else if (!phoneNumber.getText().matches("^\\d{11}$")) {
            return false;
        } else if (!password.getText().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})")) {
            return false;
        }
        return true;
    }

    public void PasswordHint(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip("-must contains one digit from 0-9\n" +
                "-must contains one lowercase characters\n" +
                "-must contains one uppercase characters\n" +
                "-length at least 4 characters and maximum of 16");
        tooltip.setFont(Font.font(13));
        passwordLabel.setTooltip(tooltip);
    }

}
