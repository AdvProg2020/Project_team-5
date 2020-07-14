package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.model.RequestForServer;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ApProject_OnlineShop.GUI.FxmlController;

import java.util.ArrayList;
import java.util.Optional;

public class Styles extends FxmlController{
    public void setTextFont(Label text) {
        text.setFont(Font.font("Times New Roman", 16));
        text.setPadding(new Insets(15));
        GridPane.setHalignment(text, HPos.CENTER);
    }
    public void setMenuButtonStyle(MenuButton menuButton) {
        menuButton.setStyle("-fx-background-color:  #dab3ff; -fx-background-radius:  8px; -fx-margin:  10px 2px; -fx-border-radius:  8px;" +
                "-fx-border-color:  #600080; -fx-border-width:  2 2 2 2;");
        menuButton.setMinSize(150, 30);
        GridPane.setValignment(menuButton, VPos.TOP);
        GridPane.setHalignment(menuButton, HPos.CENTER);
        menuButton.setFont(Font.font("Times New Roman", 18));
        menuButton.setAlignment(Pos.CENTER);
        menuButton.setCursor(Cursor.HAND);
        menuButton.setPopupSide(Side.BOTTOM);
    }


    public void setMenuItems(MenuItem menuItem) {
        menuItem.setStyle("-fx-background-color:  #dab3ff; -fx-font-size: 15;");
    }

    public GridPane makeGridPane() {
        Image logout = new Image(getClass().getClassLoader().getResource("pictures/logout.png").toString());
        ImageView logoutImage = new ImageView(logout);
        logoutImage.setFitWidth(55);
        logoutImage.setFitHeight(55);
        logoutImage.setCursor(Cursor.HAND);
        GridPane root = new GridPane();
        logoutImage.setOnMouseClicked(e -> logoutToMainMenu());
        setGridPaneStyle(root);
        Image backImage = new Image(getClass().getClassLoader().getResource("pictures/icons8-back-to-48.png").toString());
        ImageView imageView = new ImageView(backImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        Button back = new Button("", imageView);
        setBackButton(back);
        GridPane.setHalignment(back, HPos.LEFT);
        GridPane.setValignment(back, VPos.TOP);
        GridPane.setHalignment(logoutImage, HPos.LEFT);
        GridPane.setValignment(logoutImage, VPos.TOP);
        root.add(back, 0, 0);
        root.add(logoutImage, 2, 0);
        root.setHgap(10);
        root.setVgap(10);
        return root;
    }

    public void setGridPaneStyle(GridPane root) {
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffe6ff, #ffffff);");
        root.setAlignment(Pos.CENTER);
        root.setHgap(15);
        root.setHgap(15);
    }

    public void setBackButton(Button backButton) {
        backButton.setStyle("-fx-background-radius: 100%;");
        backButton.setPrefHeight(30);
        backButton.setPrefWidth(30);
        backButton.setMinSize(0, 0);
        backButton.setMaxSize(30, 30);
        backButton.setCursor(Cursor.HAND);
        backButton.setPadding(new Insets(50));
    }

    public void setVBoxStyle(VBox vBox) {
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;" +
                "-fx-background-color: linear-gradient(to bottom right, #ffb3ff, #ffffff);");
        vBox.setMinHeight(600);
        vBox.setMinWidth(400);
        vBox.setSpacing(10);
    }

    public void logoutToMainMenu() {
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
