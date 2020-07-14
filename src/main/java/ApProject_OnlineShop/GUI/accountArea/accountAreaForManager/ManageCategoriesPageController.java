package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageCategoriesPageController extends FxmlController implements Initializable {
    @FXML
    private VBox allCategoriesVBox;
    @FXML
    private VBox singleCategoryVBox;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;

    private String selectedCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateAllCategoriesBox();
    }

    private void updateAllCategoriesBox() {
        allCategoriesVBox.getChildren().clear();
        Label label = new Label("All Categories List");
        label.setFont(Font.font(15));
        label.setAlignment(Pos.CENTER);
        allCategoriesVBox.getChildren().add(label);
        List<String> allCategories = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForManagerController", "getAllCategoriesName", getToken(), null)));
        for (String category : allCategories) {
            Hyperlink hyperlink = new Hyperlink("- " + category);
            hyperlink.setOnMouseClicked(e -> {
                viewSingleCategory(category);
                removeButton.setDisable(false);
                editButton.setDisable(false);
            });
            hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            hyperlink.setAlignment(Pos.BOTTOM_LEFT);
            hyperlink.setPadding(new Insets(8));
            hyperlink.setCursor(Cursor.HAND);
            hyperlink.setUnderline(false);
            hyperlink.setPrefSize(150, 50);
            hyperlink.setFont(new Font(14));
            allCategoriesVBox.getChildren().add(hyperlink);
        }
        int size = allCategories.size() * 50;
        if (size > 422) {
            allCategoriesVBox.setPrefHeight(size);
        }
    }

    private void viewSingleCategory(String category) {
        singleCategoryVBox.getChildren().clear();
        this.selectedCategory = category;
        Label title = new Label(category);
        title.setFont(Font.font(16));
        title.setAlignment(Pos.CENTER);
        singleCategoryVBox.getChildren().add(title);
        Label details = new Label("- Properties: ");
        details.setFont(Font.font(14));
        details.setAlignment(Pos.CENTER);
        singleCategoryVBox.getChildren().add(details);
        ArrayList<String> inputs31 = new ArrayList<>();
        inputs31.add(category);
        Category cat = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findCategoryByName", null, inputs31)), Category.class);
        for (String detail : cat.getDetails()) {
            Label label = new Label(detail);
            label.setFont(Font.font(13));
            label.setAlignment(Pos.CENTER);
            singleCategoryVBox.getChildren().add(label);
        }
        Label subCats = new Label("- Sub categories: ");
        subCats.setFont(Font.font(13));
        subCats.setAlignment(Pos.CENTER);
        singleCategoryVBox.getChildren().add(subCats);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(category);
        List<String> subCategories = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForManagerController", "getAllSubCategoriesNamesOfCategory", getToken(), inputs)));
        for (String subCategory : subCategories) {
            Label label = new Label(subCategory);
            label.setFont(Font.font(12));
            label.setAlignment(Pos.CENTER);
            singleCategoryVBox.getChildren().add(label);
        }
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), getInputsForServer()));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
//            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    private void resetPage() {
        this.selectedCategory = "";
        singleCategoryVBox.getChildren().clear();
        removeButton.setDisable(true);
        editButton.setDisable(true);
        updateAllCategoriesBox();
    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onEditCategoryPressed(ActionEvent actionEvent) {
        EditCategoryPageController.setCurrentCategory(selectedCategory);
        setScene("editCategoryPage.fxml", "edit category");
    }

    public void onRemoveCategoryPressed(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "remove", "Remove Category", "are you sure to remove this category?");
        if (result.get() == ButtonType.OK) {
//            try {
//                MainController.getInstance().getAccountAreaForManagerController().removeCategory(selectedCategory);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(selectedCategory);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "removeCategory", getToken(), inputs));
            if (serverResponse.equals("category removed successfully")) {
                resetPage();
                SuccessPageFxController.showPage("successful remove", "category removed successfully");
            } else {
                ErrorPageFxController.showPage("error in remove category", serverResponse);
            }
//            } catch (Exception e) {
//                ErrorPageFxController.showPage("error in remove category", e.getMessage());
//            }
        } else
            actionEvent.consume();
    }

    public void onAddCategoryPressed(ActionEvent actionEvent) {
        setScene("addCategoryPage.fxml", "add category");
    }
}
