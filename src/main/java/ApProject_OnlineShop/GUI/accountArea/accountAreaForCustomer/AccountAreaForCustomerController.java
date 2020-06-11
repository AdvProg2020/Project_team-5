package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.*;

public class AccountAreaForCustomerController extends FxmlController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label credit;
    private ViewOrders viewOrders = new ViewOrders(this);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForCustomerController().getUserPersonalInfo();
        userName.setText(personalInfo.get(0));
        name.setText(personalInfo.get(1));
        lastName.setText(personalInfo.get(2));
        email.setText(personalInfo.get(3));
        phoneNumber.setText(personalInfo.get(4));
        credit.setText(personalInfo.get(5));
    }

    public void viewDiscountCode() {
        viewSortedDiscountCode(0);
    }

    public void viewSortedDiscountCode(int sort) {
        GridPane root = makeGridPane();
        Label topic = new Label("Discount codes");
        topic.setFont(Font.font("Times New Roman", 26));
        topic.setPadding(new Insets(5));
        GridPane.setHalignment(topic, HPos.CENTER);
        VBox vBox = new VBox();
        setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        List<String> discountCodes = MainController.getInstance().getAccountAreaForCustomerController().viewDiscountCodes(sort);
        for (String discountCode : discountCodes) {
            Hyperlink discountLink = new Hyperlink(discountCode);
            discountLink.setOnMouseClicked(e -> viewSingleDiscountCode(discountCode));
            discountLink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            discountLink.setAlignment(Pos.BOTTOM_LEFT);
            discountLink.setPadding(new Insets(8));
            discountLink.setUnderline(false);
            vBox.getChildren().add(discountLink);
        }
        root.add(topic, 1, 1);
        MenuItem sortByDiscountPercent = new MenuItem("sort by discount percent");
        MenuItem sortByEndDate = new MenuItem("sort by end date");
        MenuItem sortByMaxDiscount = new MenuItem("sort by maximum discount amount");
        sortByDiscountPercent.setOnAction(e -> viewSortedDiscountCode(1));
        sortByEndDate.setOnAction(e -> viewSortedDiscountCode(2));
        sortByMaxDiscount.setOnAction(e -> viewSortedDiscountCode(3));
        setMenuItems(sortByDiscountPercent);
        setMenuItems(sortByEndDate);
        setMenuItems(sortByMaxDiscount);
        MenuButton sorts = new MenuButton("sorts", null, sortByDiscountPercent, sortByEndDate, sortByMaxDiscount);
        setMenuButtonStyle(sorts);
        root.getChildren().get(0).setOnMouseClicked(e -> backToAccountAreaCustomer());
        root.add(sorts, 0, 2);
        StageController.setSceneJavaFx(root);
    }

    public void viewSingleDiscountCode(String summeryOfDiscountCode) {
        int index = summeryOfDiscountCode.indexOf("  ");
        String code = summeryOfDiscountCode.substring("discount code:".length(), index);
        List<String> discountCodeDetails = Shop.getInstance().findDiscountCode(code).getAllDetails();
        GridPane root = makeGridPane();
        Label discountCodeInfo = new Label("Discount Code Information");
        discountCodeInfo.setFont(Font.font("Times New Roman", 26));
        discountCodeInfo.setPadding(new Insets(7));
        GridPane.setHalignment(discountCodeInfo, HPos.CENTER);
        root.add(discountCodeInfo, 1, 1);
        VBox vBox = new VBox();
        addDiscountDetailsToVBox(discountCodeDetails, vBox);
        setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        vBox.setMinHeight(400);
        vBox.setMinWidth(600);
        vBox.setSpacing(20);
        root.getChildren().get(0).setOnMouseClicked(e -> viewDiscountCode());
        StageController.setSceneJavaFx(root);
    }

    public GridPane makeGridPane() {
        Image logout = new Image(getClass().getClassLoader().getResource("logout.png").toString());
        ImageView logoutImage = new ImageView(logout);
        logoutImage.setFitWidth(55);
        logoutImage.setFitHeight(55);
        logoutImage.setCursor(Cursor.HAND);
        GridPane root = new GridPane();
        logoutImage.setOnMouseClicked(e -> logout());
        setGridPaneStyle(root);
        Image backImage = new Image(getClass().getClassLoader().getResource("icons8-back-to-48.png").toString());
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
        return root;
    }

    public void showOrders() {
        viewOrders.viewSortedOrders(0);
    }

    public void rateProducts() {

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

    public void logout() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void setMenuItems(MenuItem menuItem) {
        menuItem.setStyle("-fx-background-color:  #dab3ff; -fx-font-size: 15;");
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

    public void backToMain(ActionEvent actionEvent) {
        setScene("mainMenuLayout.fxml", "main menu");
    }

    public void backToAccountAreaCustomer() {
        setScene("accountAreaForCustomer.fxml", "main menu");
    }

    public void addDiscountDetailsToVBox(List<String> discountCodeDetails, VBox vBox) {
        Label codeLabel = new Label("discount code:     " + discountCodeDetails.get(0));
        Label startDate = new Label("start date:     " + discountCodeDetails.get(1));
        Label endDate = new Label("end date:     " + discountCodeDetails.get(2));
        Label discountPercent = new Label("discount percent:     " + discountCodeDetails.get(3));
        Label maxDiscountAmount = new Label("maximum discount amount:     " + discountCodeDetails.get(4));
        setTextFont(codeLabel);
        setTextFont(startDate);
        setTextFont(endDate);
        setTextFont(discountPercent);
        setTextFont(maxDiscountAmount);
        vBox.getChildren().add(codeLabel);
        vBox.getChildren().add(startDate);
        vBox.getChildren().add(endDate);
        vBox.getChildren().add(discountPercent);
        vBox.getChildren().add(maxDiscountAmount);
    }

    public void setTextFont(Label text) {
        text.setFont(Font.font("Times New Roman", 16));
        text.setPadding(new Insets(20));
        GridPane.setHalignment(text, HPos.CENTER);
    }
}
