package ApProject_OnlineShop.GUI.ProductPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSupporter.AccountAreaForSupporter;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.model.RequestForServer;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.persons.Supporter;
import ApProject_OnlineShop.model.productThings.FileProduct;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class FileProductPageController extends FxmlController implements Initializable {
    public static long productId = 0;

    @FXML
    public ImageView image;
    @FXML
    public Label name;
    @FXML
    public Label subcategory;
    @FXML
    public Label downloadNumber;
    @FXML
    public Label details;
    @FXML
    public VBox sellers;
    @FXML
    public Label price;
    @FXML
    public VBox properties;
    @FXML
    private HBox rate;
    @FXML
    public Label detailsLabel;
    @FXML
    public ImageView cart;

    private static String pathBack;
    private static String titleBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (getCurrentPerson() instanceof Seller || getCurrentPerson() instanceof Manager || getCurrentPerson() instanceof Supporter) {
            cart.setVisible(false);
        }
        ArrayList<String> input32 = new ArrayList<>();
        input32.add("0");
        RequestForServer requestForServer2 = new RequestForServer("ProductController", "getProductImage", getToken(), input32);
        image.setImage(new Image(new ByteArrayInputStream(connectToServerBytes(requestForServer2))));
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        FileProduct fileProduct = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findFileProductById", null, inputs)), FileProduct.class);
        name.setText(fileProduct.getName());
        subcategory.setText(fileProduct.getSubCategory() + " category");
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
            star.setFitWidth(20);
            star.setFitHeight(20);
            rate.getChildren().add(star);
        }
        downloadNumber.setText(fileProduct.getDownloadNumber() + " downloads");
        makeSellersList();
        makeProperties();
    }

    public void backButton(ActionEvent actionEvent) {
        setScene(pathBack, titleBack);
    }

    public static void setProductId(long productId) {
        FileProductPageController.productId = productId;
    }

    private void makeProperties() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        FileProduct fileProduct = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findFileProductById", null, inputs)), FileProduct.class);
        detailsLabel.setText(fileProduct.getDescription());
        /*HashMap<String, String> categoryProperties = good.getCategoryProperties();
        properties.setAlignment(Pos.CENTER);
        properties.setSpacing(13);
        for (String detailKey : categoryProperties.keySet()) {
            HBox details = new HBox();
            details.setAlignment(Pos.CENTER_LEFT);
            details.setPrefHeight(50);
            details.setPrefWidth(599);
            VBox vbox1 = new VBox();
            vbox1.setPrefHeight(57);
            vbox1.setPrefWidth(192);
            vbox1.setAlignment(Pos.CENTER_LEFT);
            Label detailKey1 = new Label(detailKey + " :");
            detailKey1.setFont(Font.font("Times New Roman", 14));
            detailKey1.setPadding(new Insets(10));
            vbox1.getChildren().add(detailKey1);
            details.getChildren().add(vbox1);
            VBox vbox2 = new VBox();
            vbox2.setPrefHeight(57);
            vbox2.setPrefWidth(341);
            vbox2.setAlignment(Pos.CENTER);
            Label detailValue = new Label(categoryProperties.get(detailKey));
            detailValue.setFont(Font.font("Times New Roman", 14));
            detailValue.setPadding(new Insets(10));
            vbox2.getChildren().add(detailValue);
            details.getChildren().add(vbox2);
            properties.getChildren().add(details);
        }*/
    }

    public void makeSellersList() {
        sellers.setAlignment(Pos.CENTER);
        sellers.setSpacing(13);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        FileProduct fileProduct = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findFileProductById", null, inputs)), FileProduct.class);
        HBox sellerHBox = new HBox();
        sellerHBox.setAlignment(Pos.CENTER_LEFT);
        VBox usernameVBox = new VBox();
        usernameVBox.setAlignment(Pos.CENTER_LEFT);
        usernameVBox.setMinWidth(150);
        usernameVBox.setMaxWidth(150);
        Label username = new Label(fileProduct.getSeller().getUsername());
        username.setFont(Font.font("Times New Roman", 16));
        username.setPadding(new Insets(0, 15, 0, 15));
        usernameVBox.getChildren().add(username);
        sellerHBox.getChildren().add(usernameVBox);
        VBox goodStatusVBox = new VBox();
        goodStatusVBox.setAlignment(Pos.CENTER_LEFT);
        goodStatusVBox.setMinWidth(150);
        goodStatusVBox.setMaxWidth(150);
        Label goodStatus = new Label();
        goodStatus.setText("available");
        goodStatus.setFont(Font.font("Times New Roman", 16));
        goodStatus.setPadding(new Insets(0, 15, 0, 15));
        goodStatusVBox.getChildren().add(goodStatus);
        sellerHBox.getChildren().add(goodStatusVBox);
        HBox priceBox = new HBox();
        priceBox.setAlignment(Pos.CENTER_LEFT);
        priceBox.setMaxWidth(200);
        priceBox.setMinWidth(200);
        Label price = new Label("" + fileProduct.getPrice());
        price.setFont(Font.font("Times New Roman", 16));
        price.setPadding(new Insets(0, 7, 0, 7));
        priceBox.getChildren().add(price);
        Label rials = new Label("Rials");
        rials.setFont(Font.font("Times New Roman", 16));
        rials.setPadding(new Insets(0, 7, 0, 7));
        priceBox.getChildren().add(rials);
        sellerHBox.getChildren().add(priceBox);
        ImageView cartImage = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/cart.png").toString()));
        cartImage.setPickOnBounds(true);
        cartImage.setPreserveRatio(true);
        cartImage.setFitHeight(35);
        cartImage.setFitWidth(35);
        cartImage.setCursor(Cursor.HAND);
        cartImage.setOnMouseClicked(e -> onDownloadButtonPressed());
        sellerHBox.getChildren().add(cartImage);
        if (getCurrentPerson() instanceof Manager || getCurrentPerson() instanceof Seller)
            cartImage.setVisible(false);
        sellers.getChildren().add(sellerHBox);
    }

    public void goToAccountArea(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathAfterLogin(null, null);
            LoginController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForManager.fxml", "account area");
        } else if (getCurrentPerson() instanceof Supporter) {
            AccountAreaForSupporter.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForSupporter.fxml", "account area");
        }
    }

    public static void setPathBack(String pathBack, String titleBack) {
        FileProductPageController.pathBack = pathBack;
        FileProductPageController.titleBack = titleBack;
    }

    public void cart(MouseEvent mouseEvent) {
        Cart.setPathBack("fileProductPage.fxml", "file product page");
        setScene("cart.fxml", "cart");
    }

    public void onDownloadButtonPressed() {
        //TODO
    }
}
