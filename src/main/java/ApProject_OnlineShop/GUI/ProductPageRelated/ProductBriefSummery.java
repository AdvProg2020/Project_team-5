package ApProject_OnlineShop.GUI.ProductPageRelated;

import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.accountArea.Styles;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class ProductBriefSummery {
    public VBox getProductForAllProductsPage(){
        VBox mainVBox = new VBox();
        setStyleForVBox(mainVBox);
        mainVBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(getClass().getClassLoader().getResource("pictures/7.jpg").toString());
        VBox image = new VBox();
        image.getChildren().add(imageView);
        mainVBox.getChildren().add(image);
        image.setMaxSize(150,150);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        VBox nameVBox = new VBox();
        nameVBox.setAlignment(Pos.CENTER_LEFT);
        Label name = new Label("Galaxy S9 Samsung");
        (new Styles()).setTextFont(name);
        name.setPadding(new Insets(0,15,0,15));
        nameVBox.getChildren().add(name);
        mainVBox.getChildren().add(nameVBox);
        HBox rateHBox = new HBox();
        rateHBox.setAlignment(Pos.CENTER_LEFT);
        Label rate = new Label("8.0");
        rate.setPadding(new Insets(0,0,0,0));
        rateHBox.setPadding(new Insets(0,15,0,8));
        ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
        star.setFitWidth(20);
        star.setFitHeight(20);
        rateHBox.getChildren().add(star);
        rateHBox.getChildren().add(rate);
        mainVBox.getChildren().add(rateHBox);
        (new Styles()).setTextFont(rate);
        rate.setPadding(new Insets(4,0,0,4));
        VBox priceVBox = new VBox();
        priceVBox.setAlignment(Pos.CENTER_LEFT);
        priceVBox.setPadding(new Insets(0,15,0,15));
        Label price = new Label("9000" + " Rial");
        priceVBox.getChildren().add(price);
        mainVBox.getChildren().add(priceVBox);
        (new Styles()).setTextFont(price);
        price.setPadding(new Insets(0,0,0,0));
        return mainVBox;
    }

    private void setStyleForVBox(VBox gridPane){
        gridPane.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;"+
                "-fx-background-color: #ffffff;");
        gridPane.setMaxSize(250,250);
        gridPane.setMinSize(250,250);
        gridPane.setSpacing(8);
    }

    public void setScene(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(getProductForAllProductsPage(),0,0);
        StageController.setSceneJavaFx(gridPane);
    }
}
