package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditFieldPersonsController extends FxmlController implements Initializable {
    @FXML
    Label passwordLabel;
    @FXML
    PasswordField password;
    @FXML
    TextField email, phoneNumber, firstName, lastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForSellerController().getUserPersonalInfo();
        firstName.setPromptText(personalInfo.get(1));
        lastName.setPromptText(personalInfo.get(2));
        email.setPromptText(personalInfo.get(3));
        phoneNumber.setPromptText(personalInfo.get(4));
    }

    public void PasswordHint(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip("-must contains one digit from 0-9\n" +
                "-must contains one lowercase characters\n" +
                "-must contains one uppercase characters\n" +
                "-length at least 4 characters and maximum of 16");
        tooltip.setFont(Font.font(13));
        passwordLabel.setTooltip(tooltip);
    }

    public void backButtonAction(ActionEvent actionEvent) {
        if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
            setScene("accountAreaForManager.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
            setScene("accountAreaForSeller.fxml", "account area");
        }
    }

    public void logout(MouseEvent mouseEvent) {
        setScene("mainMenuLayout.fxml", "main menu");
    }

    public void editPressed(ActionEvent actionEvent) {
        boolean edited = false;
        if (checkBaseInfos()) {
            if (!firstName.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForManagerController().editField(1, firstName.getText());
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!lastName.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForManagerController().editField(2, lastName.getText());
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!email.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForManagerController().editField(3, email.getText());
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!phoneNumber.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForManagerController().editField(4, phoneNumber.getText());
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!password.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForManagerController().editField(5, password.getText());
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (edited) {
                SuccessPageFxController.showPage("field edited", "fields edited succesfully!");
            }
        }
    }

    public boolean checkBaseInfos() {
        if (!firstName.getText().matches("[a-zA-Z]{2,}") && !firstName.getText().equals("")) {
            ErrorPageFxController.showPage("Error for registering", "first name is invalid!");
            return false;
        } else if (!lastName.getText().matches("[a-zA-Z]{2,}") && !lastName.getText().equals("")) {
            ErrorPageFxController.showPage("Error for registering", "last name is invalid!");
            return false;
        } else if (!email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") && !email.getText().equals("")) {
            ErrorPageFxController.showPage("Error for registering", "email is invalid!");
            return false;
        } else if (!phoneNumber.getText().matches("^\\d{11}$") && !phoneNumber.getText().equals("")) {
            ErrorPageFxController.showPage("Error for registering", "phone number is invalid!");
            return false;
        } else if (!password.getText().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})") && !password.getText().equals("")) {
            ErrorPageFxController.showPage("Error for registering", "password is invalid!");
            return false;
        }
        return true;
    }

}