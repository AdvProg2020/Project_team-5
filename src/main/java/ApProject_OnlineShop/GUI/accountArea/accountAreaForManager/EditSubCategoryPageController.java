package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
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

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditSubCategoryPageController extends FxmlController implements Initializable {
    @FXML
    private VBox allFieldsVBox;
    @FXML
    private VBox singlePropertyVBox;
    @FXML
    private Label valueLabel;
    @FXML
    private TextField newValueField;
    @FXML
    private TextField newPropertyField;
    @FXML
    private Button editButton;

    private static String currentCategory;
    private static String currentSubCategory;
    private String selectedProperty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatePropertiesBox();
    }

    private void updatePropertiesBox() {
        allFieldsVBox.getChildren().clear();
        Label title = new Label(currentSubCategory);
        title.setFont(Font.font(18));
        title.setAlignment(Pos.CENTER);
        allFieldsVBox.getChildren().add(title);
        Label details = new Label("Properties: ");
        details.setFont(Font.font(16));
        details.setAlignment(Pos.CENTER);
        allFieldsVBox.getChildren().add(details);
        for (String detail : Shop.getInstance().findSubCategoryByName(currentSubCategory).getDetails()) {
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
            hyperlink.setPrefSize(150, 50);
            hyperlink.setUnderline(false);
            hyperlink.setFont(new Font(14));
            allFieldsVBox.getChildren().add(hyperlink);
        }
        int size = Shop.getInstance().findSubCategoryByName(currentSubCategory).getDetails().size() * 50;
        if (size > 355) {
            allFieldsVBox.setPrefHeight(size);
        }
    }

    private void viewSingleProperty(String detail) {
        this.selectedProperty = detail;
        valueLabel.setText(detail);
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

    public void onEditFieldPressed(ActionEvent actionEvent) {
        String newValue = newValueField.getText();
        if (newValue.isEmpty()) {
            ErrorPageFxController.showPage("error in editing", "please fill the text field and then click");
            return;
        }
        if (!newValue.matches(".+")) {
            ErrorPageFxController.showPage("error in editing", "wrong name format entered");
            newValueField.clear();
            return;
        }
        if (Shop.getInstance().findSubCategoryByName(currentSubCategory).getDetails().stream().anyMatch(s -> s.equalsIgnoreCase(newValue)) || Shop.getInstance().findCategoryByName(currentCategory).getDetails().stream().anyMatch(s -> s.equalsIgnoreCase(newValue))) {
            ErrorPageFxController.showPage("error in editing", "this name is already taken by another property");
            newValueField.clear();
            return;
        }
        try {
            MainController.getInstance().getAccountAreaForManagerController().editSubcategory(currentSubCategory, selectedProperty, newValue);
            newValueField.clear();
            selectedProperty = "";
            editButton.setDisable(true);
            singlePropertyVBox.setDisable(true);
            valueLabel.setText("");
            updatePropertiesBox();
            SuccessPageFxController.showPage("successful edit", "property edited successfully");
        } catch (Exception e) {
            ErrorPageFxController.showPage("error", e.getMessage());
        }
    }

    public void onAddPropertyPressed(ActionEvent actionEvent) {
        String newProperty = newPropertyField.getText();
        if (newProperty.isEmpty()) {
            ErrorPageFxController.showPage("error in editing", "please fill the text field and then click");
            return;
        }
        if (!newProperty.matches(".+")) {
            ErrorPageFxController.showPage("error in editing", "wrong name format entered");
            newPropertyField.clear();
            return;
        }
        if (Shop.getInstance().findSubCategoryByName(currentSubCategory).getDetails().stream().anyMatch(s -> s.equalsIgnoreCase(newProperty)) || Shop.getInstance().findCategoryByName(currentCategory).getDetails().stream().anyMatch(s -> s.equalsIgnoreCase(newProperty))) {
            ErrorPageFxController.showPage("error in editing", "this name is already taken by another property");
            newPropertyField.clear();
            return;
        }
        try {
            MainController.getInstance().getAccountAreaForManagerController().addPropertyToSubCategory(currentSubCategory, newProperty);
            newPropertyField.clear();
            updatePropertiesBox();
            SuccessPageFxController.showPage("successful edit", "property added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPageFxController.showPage("error", e.getMessage());
        }
    }

    public static void setCurrentInfo(String currentCategory, String currentSubCategory) {
        EditSubCategoryPageController.currentCategory = currentCategory;
        EditSubCategoryPageController.currentSubCategory = currentSubCategory;
    }
}
