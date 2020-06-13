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
    public GridPane getProductForAllProductsPage(){
        GridPane gridPane = new GridPane();
        setStyleForVBox(gridPane);
        ImageView imageView = new ImageView(getClass().getClassLoader().getResource("pictures/shopLogo.png").toString());
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        gridPane.add(imageView,0,0,2,1);
        GridPane.setHalignment(imageView, HPos.CENTER);
        Label name = new Label("Galaxy S9 Samsung");
        (new Styles()).setTextFont(name);
        name.setPadding(new Insets(4,4,0,0));
        gridPane.add(name,0,1);
        GridPane.setHalignment(name,HPos.LEFT);
        Label rate = new Label("8.0");
        rate.setPadding(new Insets(4,0,0,4));
        gridPane.add(rate,0,2 );
        GridPane.setHalignment(rate,HPos.RIGHT);
        (new Styles()).setTextFont(rate);
        Label price = new Label("9000" + " Rial");
        price.setPadding(new Insets(4,4,0,0));
        gridPane.add(price,0,3);
        (new Styles()).setTextFont(price);
        GridPane.setHalignment(price,HPos.RIGHT);
        return gridPane;
    }

    private void setStyleForVBox(GridPane gridPane){
        gridPane.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;" +
                "-fx-background-color: linear-gradient(to bottom right, #ffccff, #ffffff);");
        gridPane.setMaxSize(250,250);
        gridPane.setMinSize(250,250);
        gridPane.setVgap(8);
    }

    public void setScene(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(getProductForAllProductsPage(),0,0);
        StageController.setSceneJavaFx(gridPane);
    }
}
