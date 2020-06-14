package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditCategoryPageController extends FxmlController implements Initializable {
    @FXML
    private VBox allFieldsVBox;
    @FXML
    private Button editButton;
    @FXML
    private VBox singlePropertyVBox;
    @FXML
    private Label valueLabel;
    @FXML
    private TextField newValueField;
    @FXML
    private TextField newPropertyField;

    private static String currentCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatePropertiesBox();
    }

    private void updatePropertiesBox() {
        allFieldsVBox.getChildren().clear();
        Label title = new Label(currentCategory);
        title.setFont(Font.font(18));
        title.setAlignment(Pos.CENTER);
        allFieldsVBox.getChildren().add(title);
        Label details = new Label("Properties: ");
        details.setFont(Font.font(16));
        details.setAlignment(Pos.CENTER);
        allFieldsVBox.getChildren().add(details);
        for (String detail : Shop.getInstance().findCategoryByName(currentCategory).getDetails()) {
            Label label = new Label(detail);
            label.setFont(Font.font(14));
            label.setAlignment(Pos.CENTER);
            allFieldsVBox.getChildren().add(label);
        }
    }

    public static void setCurrentCategory(String currentCategory) {
        EditCategoryPageController.currentCategory = currentCategory;
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

    public void onEditFieldPressed(ActionEvent actionEvent) {
    }

    public void onManageSubcatsPressed(ActionEvent actionEvent) {
    }

    public void onAddPropertyPressed(ActionEvent actionEvent) {
    }
}
