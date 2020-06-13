package ApProject_OnlineShop.GUI.ProductPageRelated;

import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.accountArea.Styles;
import ApProject_OnlineShop.controller.MainController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.List;

public class ProductBriefSummery {
    public VBox getProductForAllProductsPage(int productId) {
        List<String> goodInfo = MainController.getInstance().getAllProductsController().getProductBrief(productId);
        VBox mainVBox = new VBox();
        setStyleForVBox(mainVBox);
        mainVBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image("../../../Resources/productImages/" + productId + ".jpg"));
        VBox image = new VBox();
        image.getChildren().add(imageView);
        mainVBox.getChildren().add(image);
        image.setMaxSize(150, 150);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        VBox nameVBox = new VBox();
        nameVBox.setAlignment(Pos.CENTER_LEFT);
        Label name = new Label(goodInfo.get(0));
        name.setFont(Font.font("Times New Roman", 16));
        name.setPadding(new Insets(0, 15, 0, 15));
        nameVBox.getChildren().add(name);
        mainVBox.getChildren().add(nameVBox);
        HBox rateHBox = new HBox();
        rateHBox.setAlignment(Pos.CENTER_LEFT);
        int rateInteger = Integer.parseInt(goodInfo.get(1).substring(0, 1));
        Label rate = new Label(goodInfo.get(1).substring(0, 3));
        rateHBox.setPadding(new Insets(0, 15, 0, 15));
        for (int i = 0; i < rateInteger; i++) {
            ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
            star.setFitWidth(20);
            star.setFitHeight(20);
            rateHBox.getChildren().add(star);
        }
        rateHBox.getChildren().add(rate);
        mainVBox.getChildren().add(rateHBox);
        rate.setFont(Font.font("Times New Roman", 16));
        VBox priceVBox = new VBox();
        priceVBox.setAlignment(Pos.CENTER_LEFT);
        priceVBox.setPadding(new Insets(0, 15, 0, 15));
        Label price = new Label(goodInfo.get(2));
        priceVBox.getChildren().add(price);
        mainVBox.getChildren().add(priceVBox);
        price.setFont(Font.font("Times New Roman", 16));
        return mainVBox;
    }

    private void setStyleForVBox(VBox gridPane) {
        gridPane.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;" +
                "-fx-background-color: #ffffff;");
        gridPane.setMaxSize(250, 250);
        gridPane.setMinSize(250, 250);
        gridPane.setSpacing(8);
    }

    public VBox offProductBriefSummery(long productId){
        List<String> goodInfo = MainController.getInstance().getAllProductsController().getOffProductBriefSummery(productId);
        VBox mainVBox = new VBox();
        setStyleForVBox(mainVBox);
        mainVBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image("../../../Resources/productImages/7.jpg"));
        VBox image = new VBox();
        image.getChildren().add(imageView);
        mainVBox.getChildren().add(image);
        image.setMaxSize(150, 150);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        VBox nameVBox = new VBox();
        nameVBox.setAlignment(Pos.CENTER_LEFT);
        Label name = new Label(goodInfo.get(0));
        name.setFont(Font.font("Times New Roman", 16));
        name.setPadding(new Insets(0, 15, 0, 15));
        nameVBox.getChildren().add(name);
        mainVBox.getChildren().add(nameVBox);
        HBox rateHBox = new HBox();
        rateHBox.setAlignment(Pos.CENTER_LEFT);
        int rateInteger = Integer.parseInt(goodInfo.get(1).substring(0, 1));
        Label rate = new Label(goodInfo.get(1).substring(0, 3));
        rateHBox.setPadding(new Insets(0, 15, 0, 15));
        for (int i = 0; i < rateInteger; i++) {
            ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
            star.setFitWidth(20);
            star.setFitHeight(20);
            rateHBox.getChildren().add(star);
        }
        rateHBox.getChildren().add(rate);
        mainVBox.getChildren().add(rateHBox);
        rate.setFont(Font.font("Times New Roman", 16));
        HBox priceHBox = new HBox();
        priceHBox.setAlignment(Pos.CENTER_LEFT);
        priceHBox.setPadding(new Insets(0, 15, 0, 15));
        priceHBox.setSpacing(4);
        Text primaryPrice = new Text(goodInfo.get(2));
        primaryPrice.setStrikethrough(true);
        primaryPrice.setStroke(Color.LIGHTSLATEGREY);
        primaryPrice.setStrokeType(StrokeType.INSIDE);
        Text finalPrice = new Text("  "+goodInfo.get(3));
        finalPrice.setFont(Font.font("Times New Roman", 16));
        Text rial = new Text("Rials");
        rial.setFont(Font.font("Times New Roman", 16));
        priceHBox.getChildren().add(primaryPrice);
        priceHBox.getChildren().add(finalPrice);
        priceHBox.getChildren().add(rial);
        mainVBox.getChildren().add(priceHBox);
        primaryPrice.setFont(Font.font("Times New Roman", 16));
        return mainVBox;
    }

    public void setScene(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        long id = 7;
        gridPane.getChildren().add(offProductBriefSummery(id));
        StageController.setSceneJavaFx(gridPane);
    }
}
