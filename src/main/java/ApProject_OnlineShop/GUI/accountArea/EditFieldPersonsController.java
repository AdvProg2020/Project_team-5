package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class EditFieldPersonsController extends FxmlController {
    @FXML
    Label passwordLabel;
    @FXML
    PasswordField password;
    @FXML
    TextField username, email, phoneNumber, firstName, lastName;


    public void PasswordHint(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip("-must contains one digit from 0-9\n" +
                "-must contains one lowercase characters\n" +
                "-must contains one uppercase characters\n" +
                "-length at least 4 characters and maximum of 16");
        tooltip.setFont(Font.font(13));
        passwordLabel.setTooltip(tooltip);
    }

    public void RegisterForManagerPressed(ActionEvent actionEvent) {

    }

    public void backButtonAction(ActionEvent actionEvent) {

    }

    public void logout(MouseEvent mouseEvent) {

    }
}
