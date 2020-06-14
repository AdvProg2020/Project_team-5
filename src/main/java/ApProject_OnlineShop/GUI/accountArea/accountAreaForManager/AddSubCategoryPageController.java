package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddSubCategoryPageController extends FxmlController implements Initializable {
    private static String currentCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onBackButtonPressed() {
        setScene("manageSubCategoriesPage.fxml", "manage sub categories");
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public static void setCurrentCategory(String currentCategory) {
        AddSubCategoryPageController.currentCategory = currentCategory;
    }

    public void onCreateSubCategoryPressed(ActionEvent actionEvent) {
    }

    public void onFinishButtonPressed(ActionEvent actionEvent) {
    }

    public void onAddPropertyPressed(ActionEvent actionEvent) {
    }
}
