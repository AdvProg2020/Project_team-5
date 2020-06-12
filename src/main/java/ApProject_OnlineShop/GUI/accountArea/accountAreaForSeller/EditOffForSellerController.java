package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Off;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditOffForSellerController extends FxmlController implements Initializable {
    @FXML
    public TextField maxAmount, discountPercentTextField, removeProduct, addProduct;
    @FXML
    public DatePicker startDateChooser;
    @FXML
    public DatePicker endDateChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> offDetails = MainController.getInstance().getAccountAreaForSellerController().viewOffGUI(ViewSpecificOffController.getOffId());
        maxAmount.setPromptText(offDetails.get(3));
        discountPercentTextField.setPromptText(offDetails.get(4));
        startDateChooser.setValue(LocalDate.parse(offDetails.get(1)));
        endDateChooser.setValue(LocalDate.parse(offDetails.get(2)));
    }

    public void onEditOff(ActionEvent actionEvent) {
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

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("showSpecificOffForSeller.fxml", "view off");
    }
}
