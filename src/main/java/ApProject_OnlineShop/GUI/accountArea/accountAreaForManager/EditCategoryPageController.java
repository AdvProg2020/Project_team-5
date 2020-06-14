package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.PropertyNotFoundException;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
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
    private String selectedField;

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
            Hyperlink hyperlink = new Hyperlink("- " + detail);
            hyperlink.setOnMouseClicked(e -> {
                singlePropertyVBox.setDisable(false);
                editButton.setDisable(false);
                viewSingleProperty(detail);
            });
            hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            hyperlink.setAlignment(Pos.BOTTOM_LEFT);
            hyperlink.setPadding(new Insets(8));
            hyperlink.setCursor(Cursor.HAND);
            hyperlink.setUnderline(false);
            hyperlink.setFont(new Font(14));
            allFieldsVBox.getChildren().add(hyperlink);
        }
    }

    private void viewSingleProperty(String detail) {
        this.selectedField = detail;
        valueLabel.setText(detail);
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
        String newValue = newValueField.getText();
        if (newValue.isEmpty()) {
            ErrorPageFxController.showPage("error in editing", "please fill the text field and then click");
            return;
        }
        if (!newValue.matches("\\w+")) {
            ErrorPageFxController.showPage("error in editing", "wrong name format entered");
            newValueField.clear();
            return;
        }
        try {
            MainController.getInstance().getAccountAreaForManagerController().editCategory(currentCategory, selectedField, newValue);
            newValueField.clear();
            selectedField = "";
            editButton.setDisable(true);
            singlePropertyVBox.setDisable(true);
            valueLabel.setText("");
            updatePropertiesBox();
            SuccessPageFxController.showPage("successful edit", "property edited successfully");
        } catch (Exception e) {
            ErrorPageFxController.showPage("error", e.getMessage());
        }
    }

    public void onManageSubcatsPressed(ActionEvent actionEvent) {
        ManageSubCategoriesPageController.setCurrentCategory(currentCategory);
        setScene("manageSubCategoriesPage.fxml", "manage sub categories");
    }

    public void onAddPropertyPressed(ActionEvent actionEvent) {
        String newProperty = newPropertyField.getText();
        if (newProperty.isEmpty()) {
            ErrorPageFxController.showPage("error in editing", "please fill the text field and then click");
            return;
        }
        if (!newProperty.matches("\\w+")) {
            ErrorPageFxController.showPage("error in editing", "wrong name format entered");
            newPropertyField.clear();
            return;
        }
        try {
            MainController.getInstance().getAccountAreaForManagerController().addPropertyToCategory(currentCategory, newProperty);
            newPropertyField.clear();
            updatePropertiesBox();
            SuccessPageFxController.showPage("successful edit", "property added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPageFxController.showPage("error", e.getMessage());
        }
    }
}
