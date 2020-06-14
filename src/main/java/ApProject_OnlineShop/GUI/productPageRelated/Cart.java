package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class Cart extends FxmlController implements Initializable {
    public VBox items;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Long> productIds = MainController.getInstance().getAccountAreaForCustomerController().viewInCartProducts();
        if (productIds == null || productIds.size() == 0) {
            Label isEmpty = new Label("any product doesn't exist in cart");
            isEmpty.setPadding(new Insets(15, 15, 0, 15));
            isEmpty.setFont(Font.font("Times New Roman", 20));
            items.getChildren().add(isEmpty);
        }
        for (Long productId : productIds) {
            HBox productBox = new HBox();
            setHBoxStyle(productBox);
            ImageView imageView = new ImageView(new Image(Paths.get("Resources/productImages/" + productId + ".jpg").toUri().toString()));
            imageView.setFitWidth(149);
            imageView.setFitHeight(149);
            productBox.getChildren().add(imageView);
            List<String> goodInfo = MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(productId);
            VBox textFieldVBox = new VBox();
            textFieldVBox.setMinWidth(380);
            textFieldVBox.setMaxHeight(200);
            productBox.getChildren().add(textFieldVBox);
            HBox nameHBox = new HBox();
            nameHBox.setAlignment(Pos.CENTER_LEFT);
            Label name = new Label(goodInfo.get(0));
            name.setCursor(Cursor.HAND);
            name.setOnMouseClicked(e -> showProduct(productId));
            name.setPadding(new Insets(10, 15, 0, 15));
            name.setFont(Font.font("Times New Roman", 20));
            nameHBox.getChildren().add(name);
            textFieldVBox.getChildren().add(nameHBox);
            HBox sellerUserBox = new HBox();
            sellerUserBox.setAlignment(Pos.CENTER_LEFT);
            Label seller = new Label("seller:  " + goodInfo.get(1));
            seller.setPadding(new Insets(10, 15, 10, 15));
            seller.setFont(Font.font("Times New Roman", 16));
            sellerUserBox.getChildren().add(seller);
            textFieldVBox.getChildren().add(sellerUserBox);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setMaxHeight(55);
            hBox.setMinHeight(55);
            hBox.setSpacing(15);
            HBox space = new HBox();
            space.setMinWidth(15);
            hBox.getChildren().add(space);
            HBox numberBox = new HBox();
            numberBox.setMaxHeight(35);
            numberBox.setMinHeight(35);
            numberBox.setMinWidth(70);
            numberBox.setMaxWidth(70);
            numberBox.setStyle("-fx-border-color:#000000;-fx-border-width: 1; -fx-border-style: solid;");
            Label plus = new Label("  +");
            plus.setFont(Font.font("Times New Roman", 18));
//            plus.setStyle("-fx-font-weight: bold;");
            plus.setCursor(Cursor.HAND);
            plus.setOnMouseClicked(e -> increaseProduct(productId));
            numberBox.getChildren().add(plus);
            numberBox.setAlignment(Pos.CENTER);
            Text number = new Text("   " + goodInfo.get(2) + "   ");
            numberBox.getChildren().add(number);
            number.setFont(Font.font("Times New Roman", 16));
            Label minus = new Label("- ");
            minus.setFont(Font.font("Times New Roman", 22));
            minus.setPadding(new Insets(0, 0, 4, 0));
//            minus.setStyle("-fx-font-weight: bold;");
            minus.setCursor(Cursor.HAND);
            minus.setOnMouseClicked(e -> decreaseProduct(productId));
            numberBox.getChildren().add(minus);
            hBox.getChildren().add(numberBox);
            HBox priceBox = new HBox();
            priceBox.setMinWidth(270);
            priceBox.setAlignment(Pos.CENTER_RIGHT);
            Label price = new Label(goodInfo.get(3) + " Rials");
            price.setFont(Font.font("Times New Roman", 16));
            price.setPadding(new Insets(0, 15, 0, 15));
            priceBox.getChildren().add(price);
            hBox.getChildren().add(priceBox);
            textFieldVBox.getChildren().add(hBox);
            items.getChildren().add(productBox);
        }
        items.setPrefHeight(150 * productIds.size() + 1);
    }

    public void setHBoxStyle(HBox hBox) {
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;" +
                "-fx-background-color: linear-gradient(to bottom right, #ffb3ff, #ffffff);");
        hBox.setMinHeight(150);
        hBox.setMinWidth(540);
        hBox.setMaxWidth(540);
        hBox.setMaxHeight(150);
    }

    public void decreaseProduct(long productId) {
        MainController.getInstance().getAccountAreaForCustomerController().decreaseInCartProduct(productId);
        setScene("cart.fxml", "cart");
    }

    public void increaseProduct(long productId) {
        try {
            MainController.getInstance().getAccountAreaForCustomerController().increaseInCartProduct(productId);
        } catch (Exception e) {
            ErrorPageFxController.showPage("Error happened", e.getMessage());
        }
        setScene("cart.fxml", "cart");
    }

    public void purchase() {
        setScene("purchasePage.fxml", "purchase");
    }

    public void showProduct(long productId) {
        ProductPage.setProductId(productId);
        setScene("productPage.fxml", "productPage");
    }
}
