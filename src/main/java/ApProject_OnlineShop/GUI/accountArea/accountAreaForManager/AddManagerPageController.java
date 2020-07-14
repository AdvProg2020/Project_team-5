package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddManagerPageController extends FxmlController implements Initializable {
    @FXML
    Label passwordLabel;
    @FXML
    PasswordField password;
    @FXML
    TextField username, email, phoneNumber, firstName, lastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addManagerPressed() {
        if (checkBaseInfos()) {
//            try {
//                MainController.getInstance().getAccountAreaForManagerController().createManagerAccount(username.getText(), addDetails());

            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(username.getText());
            inputs.addAll(addDetails());
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "createManagerAccount", getToken(), inputs));
            if (serverResponse.equals("user created successfully")) {
                SuccessPageFxController.showPage
                        ("successful add", "new manager registered successfully");
            } else {
                ErrorPageFxController.showPage("error for registering", serverResponse);
            }
//            } catch (Exception e) {
//                ErrorPageFxController.showPage("Error for registering", e.getMessage());
//            }
        }
    }

    public boolean checkBaseInfos() {
        if (!username.getText().matches("\\w+")) {
            ErrorPageFxController.showPage("Error for registering", "username is invalid!");
            username.clear();
            password.clear();
            return false;
        } else if (!firstName.getText().matches("[a-zA-Z]{2,}")) {
            ErrorPageFxController.showPage("Error for registering", "first name is invalid!");
            firstName.clear();
            password.clear();
            return false;
        } else if (!lastName.getText().matches("[a-zA-Z]{2,}")) {
            ErrorPageFxController.showPage("Error for registering", "last name is invalid!");
            lastName.clear();
            password.clear();
            return false;
        } else if (!email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            ErrorPageFxController.showPage("Error for registering", "email is invalid!");
            email.clear();
            password.clear();
            return false;
        } else if (!phoneNumber.getText().matches("^\\d{11}$")) {
            ErrorPageFxController.showPage("Error for registering", "phone number is invalid!");
            phoneNumber.clear();
            password.clear();
            return false;
        } else if (!password.getText().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})")) {
            ErrorPageFxController.showPage("Error for registering", "password is invalid!");
            password.clear();
            return false;
        }
        return true;
    }

    public ArrayList<String> addDetails() {
        ArrayList<String> details = new ArrayList<>();
        details.add(firstName.getText());
        details.add(lastName.getText());
        details.add(email.getText());
        details.add(phoneNumber.getText());
        details.add(password.getText());
        return details;
    }

    public void PasswordHint() {
        Tooltip tooltip = new Tooltip("-must contains one digit from 0-9\n" +
                "-must contains one lowercase characters\n" +
                "-must contains one uppercase characters\n" +
                "-length at least 4 characters and maximum of 16");
        tooltip.setFont(Font.font(13));
        passwordLabel.setTooltip(tooltip);
    }

    public void backButtonAction() {
        setScene("manageAllUsersPage.fxml", "manage users");
    }
}
