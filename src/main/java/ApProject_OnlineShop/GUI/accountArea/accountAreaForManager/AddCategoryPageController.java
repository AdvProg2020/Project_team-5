package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCategoryPageController extends FxmlController implements Initializable {
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
    private String categoryName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatePropertiesBox();
    }

    public void onBackButtonPressed() {
        setScene("manageCategoriesPage.fxml", "manage categories");
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
//            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onCreateCategoryPressed(ActionEvent actionEvent) {
        String name = nameField.getText();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(name);
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "isExistCategoryWithThisName", getToken(), inputs));
        if (name.isEmpty()) {
            ErrorPageFxController.showPage("error", "please fill field and then click");
            return;
        }
        if (!name.matches(".+")) {
            ErrorPageFxController.showPage("error", "invalid name format for category");
            nameField.clear();
            return;
        }
        if (serverResponse.equals("true")) {
            ErrorPageFxController.showPage("error in create category", "this category name is already is taken by another category");
            nameField.clear();
            return;
        }
        this.categoryName = name;
        addPropertyButton.setDisable(false);
    }

    public void onFinishButtonPressed(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "finish", "Finish adding category process", "are you sure to finish process of creating category?");
        if (result.get() == ButtonType.OK) {
//            try {
//            MainController.getInstance().getAccountAreaForManagerController().addCategory(categoryName, properties);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(categoryName);
            inputs.addAll(properties);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "addCategory", getToken(), inputs));
            if (serverResponse.equals("category added successfully")) {
                SuccessPageFxController.showPage("successfully created", "new category added successfully. please add subcategory to it later.");
                setScene("manageCategoriesPage.fxml", "manage categories");
            } else {
                ErrorPageFxController.showPage("error", serverResponse);
            }
//            } catch (Exception e) {
//                ErrorPageFxController.showPage("error", e.getMessage());
//            }
        } else
            actionEvent.consume();
    }

    public void onAddPropertyPressed(ActionEvent actionEvent) {
        String property = propertyField.getText();
        if (property.isEmpty()) {
            ErrorPageFxController.showPage("error", "please fill field and then click");
            return;
        }
        if (!property.matches(".+")) {
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
        Label title = new Label("Category Properties :");
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
