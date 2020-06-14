package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.nio.file.Paths;
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

    public void backButton(ActionEvent actionEvent) {
    }


    public void logout(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.setImage(new Image(Paths.get("Resources/productImages/"+ productId+".jpg").toUri().toString()));
        List<String> mainInfo = MainController.getInstance().getProductController().getMainInfo();
        name.setText(mainInfo.get(0));
        brand.setText(mainInfo.get(1));
        category.setText(mainInfo.get(2) + " category");
        subcategory.setText(mainInfo.get(3) + " subcategory");
        int numOfStars = Integer.parseInt(mainInfo.get(4).substring(0,1));
        for (int i = 0; i < numOfStars; i++) {
            ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
            star.setFitWidth(20);
            star.setFitHeight(20);
            rate.getChildren().add(star);
        }
        Label rateDouble = new Label("  "+mainInfo.get(4).substring(0,3));
        rateDouble.setFont(Font.font("Times New Roman", 16));
        rate.getChildren().add(rateDouble);
        views.setText(mainInfo.get(5) + " views");
        makeSellersList();
    }

    public void makeSellersList(){
        List<SellerRelatedInfoAboutGood> sellersInfo = MainController.getInstance().getProductController().getSellersInfo();
        for (SellerRelatedInfoAboutGood seller : sellersInfo) {
            HBox sellerHBox = new HBox();
            Label username = new Label(seller.getSeller().getUsername());
            username.setFont(Font.font("Times New Roman", 16));
            username.setPadding(new Insets(0,7,0,7));
            sellerHBox.getChildren().add(username);
            Label goodStatus = new Label();
            if (seller.getAvailableNumber() == 0)
                goodStatus.setText(Good.GoodStatus.NOTAVAILABLE.toString());
            if (seller.getAvailableNumber() > 0 )
                goodStatus.setText("AVAILABLE");
            goodStatus.setFont(Font.font("Times New Roman", 16));
            goodStatus.setPadding(new Insets(0,7,0,7));
            sellerHBox.getChildren().add(goodStatus);
            if (!MainController.getInstance().getProductController().isInOffBySeller(seller.getSeller())){
                HBox priceBox = new HBox();
                priceBox.setMaxWidth(130);
                priceBox.setMinWidth(130);
                Label price = new Label(""+seller.getPrice());
                price.setFont(Font.font("Times New Roman", 16));
                price.setPadding(new Insets(0,7,0,7));
                priceBox.getChildren().add(price);
                Label rials = new Label("Rials");
                rials.setFont(Font.font("Times New Roman", 16));
                rials.setPadding(new Insets(0,7,0,7));
                priceBox.getChildren().add(rials);
                sellerHBox.getChildren().add(priceBox);

            }
            if (MainController.getInstance().getProductController().isInOffBySeller(seller.getSeller())){
                HBox priceBox = new HBox();
                priceBox.setMaxWidth(130);
                priceBox.setMinWidth(130);
                Label price = new Label(""+seller.getPrice());
                price.setFont(Font.font("Times New Roman", 16));
                price.setPadding(new Insets(0,7,0,7));
                priceBox.getChildren().add(price);
                Label rials = new Label("Rials");
                rials.setFont(Font.font("Times New Roman", 16));
                rials.setPadding(new Insets(0,7,0,7));
                priceBox.getChildren().add(rials);
            }
        }
    }

    public static void setProductId(long productId) {
        ProductPage.productId = productId;
        MainController.getInstance().getProductController().setGoodById(productId);
    }

    public void addToCart(String sellerUsername){
        
    }
}
