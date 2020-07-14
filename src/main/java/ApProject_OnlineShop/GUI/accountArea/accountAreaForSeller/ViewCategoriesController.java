package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCategoriesController extends FxmlController implements Initializable {
    public VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> categories = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForManagerController", "getAllCategoriesName", getToken(), null)));
        for (String category : categories) {
            VBox vBox1 = new VBox();
            HBox cateogryHbox = new HBox();
            Label label = new Label("- " + category);
            cateogryHbox.setPrefHeight(50);
            cateogryHbox.setPrefWidth(300);
            cateogryHbox.setAlignment(Pos.BOTTOM_LEFT);
            label.setFont(javafx.scene.text.Font.font("Times New Roman", 14));
            label.setPadding(new javafx.geometry.Insets(10));
            label.setStyle("-fx-font-weight: bold;");
            cateogryHbox.getChildren().add(label);
            vBox1.getChildren().add(cateogryHbox);
            HBox subCateogryHbox = new HBox();
            Label label2 = new Label("sub categories :");
            subCateogryHbox.setPrefHeight(50);
            subCateogryHbox.setPrefWidth(400);
            subCateogryHbox.setAlignment(Pos.BOTTOM_LEFT);
            label2.setFont(javafx.scene.text.Font.font("Bookshelf Symbol 7", 12));
            label2.setPadding(new javafx.geometry.Insets(10));
            subCateogryHbox.getChildren().add(label2);
            int i = 1;
            ArrayList<String> inputs31 = new ArrayList<>();
            inputs31.add(category);
            Category cat = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findCategoryByName", null, inputs31)), Category.class);
            for (String subCategory : cat.getSubCategoriesString()) {
                Label sub = new Label((i++) + "-" + subCategory);
                sub.setFont(javafx.scene.text.Font.font("Arial", 12));
                sub.setPadding(new javafx.geometry.Insets(10));
                subCateogryHbox.getChildren().add(sub);
            }
            vBox1.getChildren().add(subCateogryHbox);
            vBox1.setPrefHeight(100);
            vBox1.setStyle("-fx-border-color:#8600b3;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-style: solid;" +
                    "-fx-background-color: linear-gradient(to bottom right, #ffb3ff, #ffffff);");
            vbox.getChildren().add(vBox1);
        }
        int size1 = (categories.size() * (100));
        if (size1 > 570) {
            vbox.setPrefWidth(size1 + 20);
        }
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("accountAreaForSeller.fxml", "account area");
    }

    public void logout(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }
}