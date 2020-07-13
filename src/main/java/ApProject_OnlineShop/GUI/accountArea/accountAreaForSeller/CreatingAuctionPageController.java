package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreatingAuctionPageController extends FxmlController implements Initializable {
    @FXML
    private TextField titleTextField;

    @FXML
    private TextField productTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private DatePicker startDateChooser;

    @FXML
    private DatePicker endDateChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onCreateAuctionPressed() {

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
        setScene("accountAreaForSeller.fxml", "account area");
    }
}
