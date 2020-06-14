package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ProductPage extends FxmlController implements Initializable {
    public static long productId = 0;
    public ImageView image;
    public Label name;
    public Label brand;
    public Label category;
    public Label subcategory;
    public HBox rate;
    public Label views;
    public Label details;
    public VBox sellers;
    public Label price;
    public VBox properties;
    public ScrollPane comments;
    public Label detailsLabel;

    public void backButton(ActionEvent actionEvent) {
    }


    public void logout(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.setImage(new Image(Paths.get("Resources/productImages/" + productId + ".jpg").toUri().toString()));
        List<String> mainInfo = MainController.getInstance().getProductController().getMainInfo();
        name.setText(mainInfo.get(0));
        brand.setText(mainInfo.get(1));
        category.setText(mainInfo.get(2) + " category");
        subcategory.setText(mainInfo.get(3) + " subcategory");
        int numOfStars = Integer.parseInt(mainInfo.get(4).substring(0, 1));
        for (int i = 0; i < numOfStars; i++) {
            ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
            star.setFitWidth(20);
            star.setFitHeight(20);
            rate.getChildren().add(star);
        }
        Label rateDouble = new Label("  " + mainInfo.get(4).substring(0, 3));
        rateDouble.setFont(Font.font("Times New Roman", 16));
        rate.getChildren().add(rateDouble);
        views.setText(mainInfo.get(5) + " views");
        makeSellersList();
        makeProperties();
    }

    private void makeProperties() {
        Good good = Shop.getInstance().findGoodById(productId);
        detailsLabel.setText(good.getDetails());
        HashMap<String, String> categoryProperties = good.getCategoryProperties();
        properties.setAlignment(Pos.CENTER);
        properties.setSpacing(13);
        for (String detailKey : categoryProperties.keySet()) {
            HBox details = new HBox();
            details.setAlignment(Pos.CENTER_LEFT);
            details.setPrefHeight(50);
            details.setPrefWidth(599);
            Label detailKey1 = new Label(detailKey + " :");
            detailKey1.setFont(Font.font("Times New Roman", 14));
            detailKey1.setPadding(new Insets(10));
            details.getChildren().add(detailKey1);
            Label detailValue = new Label(categoryProperties.get(detailKey));
            detailValue.setFont(Font.font("Times New Roman", 14));
            detailValue.setPadding(new Insets(10));
            details.getChildren().add(detailValue);
            properties.getChildren().add(details);
        }
    }

    public void makeSellersList() {
        sellers.setAlignment(Pos.CENTER);
        sellers.setSpacing(13);
        List<SellerRelatedInfoAboutGood> sellersInfo = MainController.getInstance().getProductController().getSellersInfo();
        for (SellerRelatedInfoAboutGood eachSellerInfo : sellersInfo) {
            HBox sellerHBox = new HBox();
            sellerHBox.setAlignment(Pos.CENTER_LEFT);
            VBox usernameVBox = new VBox();
            usernameVBox.setAlignment(Pos.CENTER_LEFT);
            usernameVBox.setMinWidth(150);
            usernameVBox.setMaxWidth(150);
            Label username = new Label(eachSellerInfo.getSeller().getUsername());
            username.setFont(Font.font("Times New Roman", 16));
            username.setPadding(new Insets(0, 15, 0, 15));
            usernameVBox.getChildren().add(username);
            sellerHBox.getChildren().add(usernameVBox);
            VBox goodStatusVBox = new VBox();
            goodStatusVBox.setAlignment(Pos.CENTER_LEFT);
            goodStatusVBox.setMinWidth(150);
            goodStatusVBox.setMaxWidth(150);
            Label goodStatus = new Label();
            if (eachSellerInfo.getAvailableNumber() == 0)
                goodStatus.setText("unavailable");
            if (eachSellerInfo.getAvailableNumber() > 0)
                goodStatus.setText("available");
            goodStatus.setFont(Font.font("Times New Roman", 16));
            goodStatus.setPadding(new Insets(0, 15, 0, 15));
            goodStatusVBox.getChildren().add(goodStatus);
            sellerHBox.getChildren().add(goodStatusVBox);
            if (!MainController.getInstance().getProductController().isInOffBySeller(eachSellerInfo.getSeller())) {
                HBox priceBox = new HBox();
                priceBox.setAlignment(Pos.CENTER_LEFT);
                priceBox.setMaxWidth(200);
                priceBox.setMinWidth(200);
                Label price = new Label("" + eachSellerInfo.getPrice());
                price.setFont(Font.font("Times New Roman", 16));
                price.setPadding(new Insets(0, 7, 0, 7));
                priceBox.getChildren().add(price);
                Label rials = new Label("Rials");
                rials.setFont(Font.font("Times New Roman", 16));
                rials.setPadding(new Insets(0, 7, 0, 7));
                priceBox.getChildren().add(rials);
                sellerHBox.getChildren().add(priceBox);

            }
            if (MainController.getInstance().getProductController().isInOffBySeller(eachSellerInfo.getSeller())) {
                HBox priceBox = new HBox();
                priceBox.setAlignment(Pos.CENTER_LEFT);
                priceBox.setMaxWidth(200);
                priceBox.setMinWidth(200);
                Text primaryPrice = new Text("" + eachSellerInfo.getPrice());
                primaryPrice.setStrikethrough(true);
                primaryPrice.setStroke(Color.LIGHTSLATEGREY);
                primaryPrice.setStrokeType(StrokeType.INSIDE);
                priceBox.getChildren().add(primaryPrice);
                Label finalPrice = new Label("" + Shop.getInstance().getFinalPriceOfAGood(Shop.getInstance().findGoodById(productId), eachSellerInfo.getSeller()));
                finalPrice.setFont(Font.font("Times New Roman", 16));
                finalPrice.setPadding(new Insets(0, 7, 0, 7));
                priceBox.getChildren().add(finalPrice);
                Label rials = new Label("Rials");
                rials.setFont(Font.font("Times New Roman", 16));
                rials.setPadding(new Insets(0, 7, 0, 7));
                priceBox.getChildren().add(rials);
                sellerHBox.getChildren().add(priceBox);
            }
            ImageView cartImage = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/cart.png").toString()));
            cartImage.setFitHeight(35);
            cartImage.setFitWidth(35);
            cartImage.setOnMouseClicked(e -> addToCart(eachSellerInfo.getSeller().getUsername()));
            sellerHBox.getChildren().add(cartImage);
            sellers.getChildren().add(sellerHBox);
        }
    }

    public static void setProductId(long productId) {
        ProductPage.productId = productId;
        MainController.getInstance().getProductController().setGoodById(productId);
    }

    public void addToCart(String sellerUsername) {
        System.out.println(sellerUsername);
    }
}
