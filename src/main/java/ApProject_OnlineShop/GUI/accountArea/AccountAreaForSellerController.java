package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountAreaForSellerController extends FxmlController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForSellerController().getUserPersonalInfo();
        userName.setText(personalInfo.get(0));
        name.setText(personalInfo.get(1));
        lastName.setText(personalInfo.get(2));
        email.setText(personalInfo.get(3));
        phoneNumber.setText(personalInfo.get(4));
    }

    public void viewCompanyInformation(MouseEvent mouseEvent) {
    }

    public void viewSaleHistory(MouseEvent mouseEvent) {
    }

    public void manageProducts(MouseEvent mouseEvent) {
    }

    public void showCategories(MouseEvent mouseEvent) {
    }

    public void viewOffs(MouseEvent mouseEvent) {
    }

    public void viewBalance(MouseEvent mouseEvent) {
    }
}
