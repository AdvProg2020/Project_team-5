package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.Main;
import ApProject_OnlineShop.controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AccountAreaForCustomerController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label credit;


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

    public void viewDiscountCode(MouseEvent mouseEvent) {
        GridPane root = makeGridPane();
        Label topic = new Label("Discount codes");
        topic.setFont(Font.font("Times New Roman",26));
        topic.setPadding(new Insets(10));
        GridPane.setHalignment(topic,HPos.CENTER);
        VBox vBox = new VBox();
        setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        List<String> discountCodes = MainController.getInstance().getAccountAreaForCustomerController().viewDiscountCodes();
        for (String discountCode : discountCodes) {
            Hyperlink discountLink = new Hyperlink(discountCode);
            discountLink.setOnMouseClicked(e -> viewSingleDiscountCode(discountCode));
            discountLink.setStyle("-fx-color-fill: #250033;");
            discountLink.setAlignment(Pos.BOTTOM_LEFT);
            discountLink.setPadding(new Insets(15));
            vBox.getChildren().add(discountLink);
        }
        root.add(topic,1,1);
        StageController.setSceneJavaFx(root);
    }

    public void viewSingleDiscountCode(String discountCode) {

    }

    public GridPane makeGridPane() {
        GridPane root = new GridPane();
        setGridPaneStyle(root);
        Image backImage = new Image(getClass().getClassLoader().getResource("icons8-back-to-48.png").toString());
        ImageView imageView = new ImageView(backImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        Button back = new Button("", imageView);
        setBackButton(back);
        GridPane.setHalignment(back, HPos.LEFT);
        GridPane.setValignment(back, VPos.TOP);
        root.add(back, 0, 0);
        return root;
    }

    public void showOrders(ActionEvent actionEvent) {
        System.out.println("hello");
    }

    public void rateProducts(ActionEvent actionEvent) {

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
//        vBox.setMaxSize(500, 700);
        vBox.setSpacing(30);
    }

}
