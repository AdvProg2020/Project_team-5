package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddSubCategoryPageController extends FxmlController implements Initializable {
    @FXML
    private VBox propertiesBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField propertyField;
    @FXML
    private Button addPropertyButton;
    @FXML
    private Button finishButton;

    private ArrayList<String> properties = new ArrayList<>();
    private String subCategoryName;

    private static String currentCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatePropertiesBox();
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
        String name = nameField.getText();
        if (name.isEmpty()) {
            ErrorPageFxController.showPage("error", "please fill field and then click");
            return;
        }
        if (!name.matches("\\w+")) {
            ErrorPageFxController.showPage("error", "invalid name format for sub category");
            nameField.clear();
            return;
        }
        if (MainController.getInstance().getAccountAreaForManagerController().isExistSubcategoryWithThisName(name)) {
            ErrorPageFxController.showPage("error in create sub category", "this category name is already is taken by another sub category");
            nameField.clear();
            return;
        }
        this.subCategoryName = name;
        addPropertyButton.setDisable(false);
    }

    public void onFinishButtonPressed(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "finish", "Finish adding sub category process", "are you sure to finish process of creating sub category?");
        if (result.get() == ButtonType.OK) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().addSubcategory(currentCategory, subCategoryName, properties);
                SuccessPageFxController.showPage("successfully created", "new category added successfully");
                setScene("manageSubCategoriesPage.fxml", "manage sub categories");
            } catch (Exception e) {
                ErrorPageFxController.showPage("error", e.getMessage());
            }
        } else
            actionEvent.consume();
    }

    public void onAddPropertyPressed(ActionEvent actionEvent) {
        String property = propertyField.getText();
        if (property.isEmpty()) {
            ErrorPageFxController.showPage("error", "please fill field and then click");
            return;
        }
        if (!property.matches("\\w+")) {
            ErrorPageFxController.showPage("error", "invalid name format for property");
            propertyField.clear();
            return;
        }
        if (properties.stream().anyMatch(s -> s.equalsIgnoreCase(property))) {
            ErrorPageFxController.showPage("error", "this name is already taken by another property");
            propertyField.clear();
            return;
        }
        properties.add(property);
        propertyField.clear();
        finishButton.setDisable(false);
        updatePropertiesBox();
    }

    private void updatePropertiesBox() {
        propertiesBox.getChildren().clear();
        Label title = new Label("Sub Category Properties :");
        title.setFont(Font.font(16));
        title.setAlignment(Pos.CENTER);
        propertiesBox.getChildren().add(title);
        for (String detail : properties) {
            Label label = new Label(detail);
            label.setFont(Font.font(13));
            label.setAlignment(Pos.CENTER);
            propertiesBox.getChildren().add(label);
        }
    }
}
