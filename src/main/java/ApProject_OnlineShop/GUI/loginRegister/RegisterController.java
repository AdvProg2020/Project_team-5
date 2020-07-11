package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.Shop;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Optional;

public class RegisterController extends FxmlController {
    public PasswordField repeatPassword;
    public PasswordField bankPassword;
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
        if (checkBaseInfos("customer")) {
            if (!credit.getText().matches("\\d\\d\\d\\d+")) {
                ErrorPageFxController.showPage("Error for registering", "credit is invalid!");
            } else {
                RegisterControllerPart2.setDetails2(addDetails("customer"));
                RegisterControllerPart2.setRole("customer");
                RegisterControllerPart2.setUserName(username.getText());
                setScene("getPhotoForUsers.fxml", "register");
            }
        }
    }

    public void RegisterForManagerPressed(ActionEvent actionEvent) {
        if (checkBaseInfos("manager")) {
            RegisterControllerPart2.setDetails2(addDetails("manager"));
            RegisterControllerPart2.setRole("manager");
            RegisterControllerPart2.setUserName(username.getText());
            setScene("getPhotoForUsers.fxml", "register");
        }
    }

    public void RegisterForSellerPressed(ActionEvent actionEvent) {
        if (checkBaseInfos("seller")) {
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
                RegisterControllerPart2.setDetails2(addDetails("seller"));
                RegisterControllerPart2.setRole("seller");
                RegisterControllerPart2.setUserName(username.getText());
                setScene("getPhotoForUsers.fxml", "register");
            }
        }
    }

    public boolean checkBaseInfos(String role) {
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
        if (role.equals("customer") || role.equals("seller")){
            if (!bankPassword.getText().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})")){
                ErrorPageFxController.showPage("Error for registering", "bank password is invalid!");
                return false;
            }
            else if (!bankPassword.getText().equals(repeatPassword.getText())){
                ErrorPageFxController.showPage("Error for registering", "passwords do not match");
                return false;
            }
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

    public ArrayList<String> addDetails(String role) {
        ArrayList<String> details = new ArrayList<>();
        details.add(firstName.getText());
        details.add(lastName.getText());
        details.add(email.getText());
        details.add(phoneNumber.getText());
        details.add(password.getText());
        if (role.equals("customer")) {
            details.add(credit.getText());
            details.add(bankPassword.getText());
            details.add(repeatPassword.getText());
        } else if (role.equals("seller")) {
            details.add(companyName.getText());
            details.add(companyWebsite.getText());
            details.add(companyPhoneNumber.getText());
            details.add(companyFaxNumber.getText());
            details.add(companyAddress.getText());
            details.add(bankPassword.getText());
            details.add(repeatPassword.getText());
        }
        return details;
    }

    public void backButtonAction(ActionEvent actionEvent) {
        if (Shop.getInstance().getAllPersons().size() == 0) {
            Optional<ButtonType> result = new FxmlController().showAlert
                    (Alert.AlertType.CONFIRMATION, "Exit", "Exit", "are you sure to exit shop?");
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
        }
        setScene("login.fxml", "Login");
    }

}
