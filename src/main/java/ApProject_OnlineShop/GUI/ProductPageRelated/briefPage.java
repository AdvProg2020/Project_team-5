package ApProject_OnlineShop.GUI.ProductPageRelated;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class briefPage {
    public void getProductForAllProductsPage(){
        GridPane gridPane = new GridPane();
        setStyleForVBox(gridPane);
        ImageView imageView = new ImageView("./Resources/productImages/7.png");
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        gridPane.getChildren().add(imageView);
        GridPane.setHalignment(imageView, HPos.CENTER);
        
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
}
