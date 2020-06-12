package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountAreaForSellerController extends FxmlController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label balance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForSellerController().getUserPersonalInfo();
        userName.setText(personalInfo.get(0));
        name.setText(personalInfo.get(1));
        lastName.setText(personalInfo.get(2));
        email.setText(personalInfo.get(3));
        phoneNumber.setText(personalInfo.get(4));
        balance.setText(MainController.getInstance().getAccountAreaForSellerController().viewBalance() + "");
    }

    public void viewCompanyInformation(MouseEvent mouseEvent) {
        setScene("showCompanyInformation.fxml", "Copmany Information");
    }

    public void viewSaleHistory(MouseEvent mouseEvent) {
        (new ViewOrdersForSeller()).viewSortedOrders(0);
    }

    public void manageProducts(MouseEvent mouseEvent) {
        setScene("manageProductsForSeller.fxml","manage products");
    }

    public void showCategories(MouseEvent mouseEvent) {
    }

    public void viewOffs(MouseEvent mouseEvent) {
        setScene("viewOffsForSeller.fxml", "Offs");
    }

    public void logout(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("mainMenuLayout.fxml", "Main menu");
    }

    public void editField(ActionEvent actionEvent) {
        setScene("editFieldPersons.fxml", "edit field");
    }
}
