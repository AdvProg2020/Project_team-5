package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class AddProductPart1 extends FxmlController {
    @FXML
    public TextField name,brand,price,additionalDetails,availableNumber,subCategory;

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("manageProducts.fxml","manage products");
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onAddProduct(ActionEvent actionEvent) {
        if(checkBaseInfos()){

        }
    }

    private boolean checkBaseInfos() {
        if (!name.getText().matches("\\w+")) {
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
}
