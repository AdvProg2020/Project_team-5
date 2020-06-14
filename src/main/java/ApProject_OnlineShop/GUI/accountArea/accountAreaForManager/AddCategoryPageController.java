package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCategoryPageController extends FxmlController implements Initializable {
    @FXML
    private VBox propertiesBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField propertyField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onBackButtonPressed() {
        setScene("manageCategoriesPage.fxml", "account area");
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onCreateCategoryPressed(ActionEvent actionEvent) {
    }

    public void onFinishButtonPressed(ActionEvent actionEvent) {
    }

    public void onAddPropertyPressed(ActionEvent actionEvent) {
    }
}
