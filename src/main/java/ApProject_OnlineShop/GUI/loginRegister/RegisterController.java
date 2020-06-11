package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
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
    @FXML
    TextField companyName, companyAddress, companyWebsite, companyPhoneNumber, companyFaxNumber;


    public void RegisterForCustomerPressed(ActionEvent actionEvent) {
        if (checkBaseInfos()) {
            if (!credit.getText().matches("\\d\\d\\d\\d+")) {
                ErrorPageFxController.showPage("Error for registering", "credit is invalid!");
            } else {
                try {
                    MainController.getInstance().getLoginRegisterController().createAccount
                            ("customer", username.getText(), addDeatails("customer"));
                    SuccessPageFxController.showPage("Register was successful",
                            "you registered successfully");
                    setScene("login.fxml","Login");
                } catch (UsernameIsTakenAlreadyException | FileCantBeSavedException | MainManagerAlreadyRegistered | IOException e) {
                    ErrorPageFxController.showPage("Error for registering", e.getMessage());
                }
            }
        }
    }

    public void RegisterForManagerPressed(ActionEvent actionEvent) {
        if (checkBaseInfos()) {
            try {
                MainController.getInstance().getLoginRegisterController()
                        .createAccount("manager", username.getText(), addDeatails("manager"));
                SuccessPageFxController.showPage
                        ("Register was successful", "you registered successfully");
            } catch (UsernameIsTakenAlreadyException | MainManagerAlreadyRegistered | IOException | FileCantBeSavedException e) {
                ErrorPageFxController.showPage("Error for registering", e.getMessage());
            }
        }
    }

    public void RegisterForSellerPressed(ActionEvent actionEvent) {
        if (checkBaseInfos()) {
            if (!companyName.getText().matches("[a-zA-Z]{1,}")) {
                ErrorPageFxController.showPage("Error for registering", "company name is invalid!");
            } else if (!companyWebsite.getText()
                    .matches("^(https?:\\/\\/)?(www\\.)?([a-zA-Z0-9]+(-?[a-zA-Z0-9])*\\.)+[\\w]{2,}(\\/\\S*)?$")) {
                ErrorPageFxController.showPage("Error for registering", "website is invalid!");
            } else if (!companyPhoneNumber.getText().matches("^\\d{11}$")) {
                ErrorPageFxController.showPage("Error for registering", "company phone number is invalid!");
            } else if (!companyFaxNumber.getText().matches("^(\\d+){6,}$")) {
                ErrorPageFxController.showPage("Error for registering", "company fax number is invalid!");
            } else {
                try {
                    MainController.getInstance().getLoginRegisterController()
                            .createAccount("seller", username.getText(), addDeatails("seller"));
                    SuccessPageFxController
                            .showPage("Register was successful", "you registered successfully");
                } catch (UsernameIsTakenAlreadyException | MainManagerAlreadyRegistered | IOException | FileCantBeSavedException e) {
                    ErrorPageFxController.showPage("Error for registering", e.getMessage());
                }
            }
        }
    }

    public boolean checkBaseInfos() {
        if (!username.getText().matches("\\w+")) {
            ErrorPageFxController.showPage("Error for registering", "username is invalid!");
            return false;
        } else if (!firstName.getText().matches("[a-zA-Z]{2,}")) {
            ErrorPageFxController.showPage("Error for registering", "first name is invalid!");
            return false;
        } else if (!lastName.getText().matches("[a-zA-Z]{2,}")) {
            ErrorPageFxController.showPage("Error for registering", "last name is invalid!");
            return false;
        } else if (!email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            ErrorPageFxController.showPage("Error for registering", "email is invalid!");
            return false;
        } else if (!phoneNumber.getText().matches("^\\d{11}$")) {
            ErrorPageFxController.showPage("Error for registering", "phone number is invalid!");
            return false;
        } else if (!password.getText().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})")) {
            ErrorPageFxController.showPage("Error for registering", "password is invalid!");
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

    public ArrayList<String> addDeatails(String role) {
        ArrayList<String> details = new ArrayList<>();
        details.add(firstName.getText());
        details.add(lastName.getText());
        details.add(email.getText());
        details.add(phoneNumber.getText());
        details.add(password.getText());
        if (role.equals("customer")) {
            details.add(credit.getText());

        } else if (role.equals("seller")) {
            details.add(companyName.getText());
            details.add(companyWebsite.getText());
            details.add(companyPhoneNumber.getText());
            details.add(companyFaxNumber.getText());
            details.add(companyAddress.getText());
        }
        return details;
    }

    public void backButtonAction(ActionEvent actionEvent) {
        setScene("login.fxml", "Login");
    }

}
