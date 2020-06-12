package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class CreatingOffForSellerController extends FxmlController {
    public TextField maxAmount, discountPercentTextField;
    @FXML
    public DatePicker startDateChooser;
    @FXML
    public DatePicker endDateChooser;
    @FXML
    public TextField productToAdd;

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("viewOffsForSeller.fxml", "Offs");
    }

    public void onCreateDiscountCodePressed(ActionEvent actionEvent) {

    }

    public void onFinishButtonPressed(ActionEvent actionEvent) {
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

    public void onAddProductPressed(ActionEvent actionEvent) {
    }
}
