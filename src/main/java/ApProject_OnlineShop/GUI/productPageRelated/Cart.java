package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        for (Long productId : productIds) {
            HBox productBox = new HBox();
            setHBoxStyle(productBox);
            ImageView imageView = new ImageView(new Image(Paths.get("Resources/productImages/" + productId + ".jpg").toUri().toString()));
            imageView.setFitWidth(199.5);
            imageView.setFitHeight(199.5);
            productBox.getChildren().add(imageView);
            List<String> goodInfo = MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(productId);
            VBox textFieldVBox = new VBox();
            textFieldVBox.setMaxHeight(200);
            productBox.getChildren().add(textFieldVBox);
            HBox nameHBox = new HBox();
            nameHBox.setAlignment(Pos.CENTER_LEFT);
            Label name = new Label(goodInfo.get(0));
            name.setPadding(new Insets(0,15,0,15));
            name.setFont(Font.font("Times New Roman", 20));
            nameHBox.getChildren().add(name);
            textFieldVBox.getChildren().add(nameHBox);
            HBox sellerUserBox = new HBox();
            sellerUserBox.setAlignment(Pos.CENTER_LEFT);
            Label seller = new Label(goodInfo.get(1));
            seller.setPadding(new Insets(10,15,10,15));
            seller.setFont(Font.font("Times New Roman", 16));
            sellerUserBox.getChildren().add(seller);
            textFieldVBox.getChildren().add(sellerUserBox);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setMaxHeight(55);
            hBox.setMinHeight(55);
            hBox.setSpacing(15);
            HBox numberBox = new HBox();
            numberBox.setMaxHeight(35);
            numberBox.setMinHeight(35);
            numberBox.setStyle("-fx-border-color:#8600b3;-fx-border-width: 1; -fx-border-style: solid;");
            Text plus = new Text(" +");
            plus.setFont(Font.font("Times New Roman", 16));
            plus.setStyle("-fx-font-weight: bold;");
            //plus.setOnMouseClicked(e -> MainController.getInstance().getAccountAreaForCustomerController().increaseInCartProduct(productId));
            numberBox.getChildren().add(plus);

            Text minus = new Text(" +");
            minus.setFont(Font.font("Times New Roman", 16));
            minus.setStyle("-fx-font-weight: bold;");
            minus.setOnMouseClicked(e -> MainController.getInstance().getAccountAreaForCustomerController().decreaseInCartProduct(productId));
            numberBox.getChildren().add(minus);

            items.getChildren().add(productBox);
        }
    }
    public void setHBoxStyle(HBox hBox) {
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-border-color:#8600b3;" +
                "-fx-border-width: 1;" +
                "-fx-border-style: solid;" +
                "-fx-background-color: linear-gradient(to bottom right, #ffb3ff, #ffffff);");
        hBox.setMinHeight(200);
        hBox.setMinWidth(540);
        hBox.setMaxWidth(540);
        hBox.setMaxHeight(200);
    }

    public void decreaseProduct(long productId){

    }

    public void increaseProduct(long productId){
        
    }
}
