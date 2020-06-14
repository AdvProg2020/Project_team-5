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
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageSubCategoriesPageController extends FxmlController implements Initializable {
    @FXML
    private VBox allSubCategoriesVBox;
    @FXML
    private VBox singleSubCategoryVBox;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;

    private static String currentCategory;
    private String selectedSubCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateAllSubCategoriesBox();
    }

    private void updateAllSubCategoriesBox() {
        allSubCategoriesVBox.getChildren().clear();
        Label label = new Label("All Sub Categories List");
        label.setFont(Font.font(15));
        label.setAlignment(Pos.CENTER);
        allSubCategoriesVBox.getChildren().add(label);
        for (String subCategory : MainController.getInstance().getAccountAreaForManagerController().getCategorySubCatsNames(currentCategory)) {
            Hyperlink hyperlink = new Hyperlink("- " + subCategory);
            hyperlink.setOnMouseClicked(e -> {
                viewSingleSubCategory(subCategory);
                removeButton.setDisable(false);
                editButton.setDisable(false);
            });
            hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            hyperlink.setAlignment(Pos.BOTTOM_LEFT);
            hyperlink.setPadding(new Insets(8));
            hyperlink.setCursor(Cursor.HAND);
            hyperlink.setUnderline(false);
            hyperlink.setFont(new Font(14));
            allSubCategoriesVBox.getChildren().add(hyperlink);
        }
    }

    private void viewSingleSubCategory(String subCategory) {
        singleSubCategoryVBox.getChildren().clear();
        this.selectedSubCategory = subCategory;
        Label title = new Label(subCategory);
        title.setFont(Font.font(16));
        title.setAlignment(Pos.CENTER);
        singleSubCategoryVBox.getChildren().add(title);
        Label details = new Label("- Properties: ");
        details.setFont(Font.font(14));
        details.setAlignment(Pos.CENTER);
        singleSubCategoryVBox.getChildren().add(details);
        for (String detail : Shop.getInstance().findSubCategoryByName(subCategory).getDetails()) {
            Label label = new Label(detail);
            label.setFont(Font.font(13));
            label.setAlignment(Pos.CENTER);
            singleSubCategoryVBox.getChildren().add(label);
        }
    }

    private void resetPage() {
        this.selectedSubCategory = "";
        singleSubCategoryVBox.getChildren().clear();
        removeButton.setDisable(true);
        editButton.setDisable(true);
        updateAllSubCategoriesBox();
    }

    public void onBackButtonPressed() {
        setScene("manageCategoriesPage.fxml", "manage categories");
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
        ManageSubCategoriesPageController.currentCategory = currentCategory;
    }

    public void onRemoveSubCategoryPressed(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "remove", "Remove Category", "are you sure to remove this category?");
        if (result.get() == ButtonType.OK) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeSubCategory(currentCategory, selectedSubCategory);
                resetPage();
                SuccessPageFxController.showPage("successful remove", "subcategory removed successfully");
            } catch (Exception e) {
                ErrorPageFxController.showPage("error in remove subcategory", e.getMessage());
            }
        } else
            actionEvent.consume();
    }

    public void onEditSubCategoryPressed(ActionEvent actionEvent) {
    }

    public void onAddSubCategoryPressed(ActionEvent actionEvent) {
        AddSubCategoryPageController.setCurrentCategory(currentCategory);
        setScene("addSubCategoryPage.fxml", "adding subcategory");
    }
}
