package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountAreaForCustomerController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label credit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForCustomerController().getUserPersonalInfo();
//        userName.setText(personalInfo.get(0));
//        name.setText(personalInfo.get(1));
//        lastName.setText(personalInfo.get(2));
//        email.setText(personalInfo.get(3));
//        phoneNumber.setText(personalInfo.get(4));
//        credit.setText(personalInfo.get(5));
    }
}
