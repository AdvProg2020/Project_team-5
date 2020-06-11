package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
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

    }

    public void logout(MouseEvent mouseEvent) {

    }

    public void editPressed(ActionEvent actionEvent) {
    }
}
