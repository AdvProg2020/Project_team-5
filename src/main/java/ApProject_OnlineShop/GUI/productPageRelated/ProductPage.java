package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
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
    public ImageView cart;
    private static String pathBack;
    private static String titleBack;

    public void backButton(ActionEvent actionEvent) {
        setScene(pathBack, titleBack);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (getCurrentPerson() instanceof Seller || getCurrentPerson() instanceof Manager) {
            cart.setVisible(false);
        }
        ArrayList<String> input32 = new ArrayList<>();
        input32.add("" + productId);
        RequestForServer requestForServer2 = new RequestForServer("ProductController", "getProductImage", getToken(), input32);
        image.setImage(new Image(new ByteArrayInputStream(connectToServerBytes(requestForServer2))));
//        image.setImage(new Image(Paths.get("Resources/productImages/" + productId + ".jpg").toUri().toString()));
//        List<String> mainInfo = MainController.getInstance().getProductController().getMainInfo();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        ArrayList<String> mainInfo = convertStringToArraylist(connectToServer(new RequestForServer("ProductController", "getMainInfo", null, inputs)));
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
        ArrayList<String> inputs22 = new ArrayList<>();
        inputs22.add(productId + "");
        Good good = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findGoodById", null, inputs22)), Good.class);        detailsLabel.setText(good.getDetails());
        HashMap<String, String> categoryProperties = good.getCategoryProperties();
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
        }
    }

    public void makeSellersList() {
        sellers.setAlignment(Pos.CENTER);
        sellers.setSpacing(13);
//        List<SellerRelatedInfoAboutGood> sellersInfo = MainController.getInstance().getProductController().getSellersInfo();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        List<SellerRelatedInfoAboutGood> sellersInfo = new Gson().fromJson(connectToServer(new RequestForServer("ProductController", "getSellersInfo", null, inputs)), new TypeToken<List<SellerRelatedInfoAboutGood>>() {
        }.getType());
        for (SellerRelatedInfoAboutGood eachSellerInfo : sellersInfo) {
            HBox sellerHBox = new HBox();
            sellerHBox.setAlignment(Pos.CENTER_LEFT);
            VBox usernameVBox = new VBox();
            usernameVBox.setAlignment(Pos.CENTER_LEFT);
            usernameVBox.setMinWidth(150);
            usernameVBox.setMaxWidth(150);
            Label username = new Label(eachSellerInfo.getSellerUserName());
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
            ArrayList<String> inputs2 = new ArrayList<>();
            inputs2.add(eachSellerInfo.getSellerUserName());
            inputs2.add(productId + "");
            if (connectToServer(new RequestForServer("ProductController", "isInOffBySeller", null, inputs2)).equals("false")) {
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
            if (connectToServer(new RequestForServer("ProductController", "isInOffBySeller", null, inputs2)).equals("true")) {
                HBox priceBox = new HBox();
                priceBox.setAlignment(Pos.CENTER_LEFT);
                priceBox.setMaxWidth(200);
                priceBox.setMinWidth(200);
                Text primaryPrice = new Text("" + eachSellerInfo.getPrice());
                primaryPrice.setStrikethrough(true);
                primaryPrice.setStroke(Color.LIGHTSLATEGREY);
                primaryPrice.setStrokeType(StrokeType.INSIDE);
                priceBox.getChildren().add(primaryPrice);
                ArrayList<String> inputs22 = new ArrayList<>();
                inputs22.add(productId + "");
                Good good = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findGoodById", null, inputs22)), Good.class);
                ArrayList<String> inputs44 = new ArrayList<>();
                inputs44.add(good.getGoodId() + "");
                inputs44.add(eachSellerInfo.getSellerUserName());
                String finalPrice1 = connectToServer(new RequestForServer("Shop", "getFinalPriceOfAGood", null, inputs44));
                Label finalPrice = new Label(finalPrice1);
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
            cartImage.setPickOnBounds(true);
            cartImage.setPreserveRatio(true);
            cartImage.setFitHeight(35);
            cartImage.setFitWidth(35);
            cartImage.setCursor(Cursor.HAND);
            cartImage.setOnMouseClicked(e -> addToCart(eachSellerInfo.getSellerUserName()));
            sellerHBox.getChildren().add(cartImage);
            if (getCurrentPerson() instanceof Manager || getCurrentPerson() instanceof Seller)
                cartImage.setVisible(false);
            sellers.getChildren().add(sellerHBox);
        }
    }

    public static void setProductId(long productId) {
        ProductPage.productId = productId;
//        MainController.getInstance().getProductController().setGoodById(productId);
    }

    public void addToCart(String sellerUsername) {
//        try {
//            MainController.getInstance().getProductController().addGoodToCartGUI(sellerUsername,productId,getId());
//            SuccessPageFxController.showPage("product added to cart", "product added to cart succesfully!");
//        } catch (DontHaveEnoughNumberOfThisProduct | NotEnoughAvailableProduct dontHaveEnoughNumberOfThisProduct) {
//            ErrorPageFxController.showPage("cannot add this product", dontHaveEnoughNumberOfThisProduct.getMessage());
//        } catch (Exception exception) {
//            ErrorPageFxController.showPage("cannot add this product", exception.getMessage());
//        }
        ArrayList<String> inputs2 = new ArrayList<>();
        inputs2.add(sellerUsername);
        inputs2.add(productId + "");
        inputs2.add(getId() + "");
        String serverResponse = connectToServer(new RequestForServer("ProductController", "addGoodToCartGUI", null, inputs2));
        if (serverResponse.equals("successfully added to cart")) {
            SuccessPageFxController.showPage("product added to cart", "product added to cart successfully!");
        } else {
            ErrorPageFxController.showPage("cannot add this product", serverResponse);
        }
    }

    public void goToAccountArea(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathAfterLogin(null, null);
            LoginController.setPathBack("productPage.fxml", "product page");
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("productPage.fxml", "product page");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("productPage.fxml", "product page");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("productPage.fxml", "product page");
            setScene("accountAreaForManager.fxml", "account area");
        }
    }

    public void showComments(ActionEvent actionEvent) {
        CommentsPage.setGoodId(productId);
        CommentsPage.setPathBack("productPage.fxml", "product page");
        setScene("commentsPage.fxml", "comments");
    }

    public void compare(ActionEvent actionEvent) {
        setScene("allProductsForCompareProduct.fxml", "compare");
    }

    public static void setPathBack(String pathBack, String titleBack) {
        ProductPage.pathBack = pathBack;
        ProductPage.titleBack = titleBack;
    }

    public void cart(MouseEvent mouseEvent) {
        Cart.setPathBack("productPage.fxml", "product page");
        setScene("cart.fxml", "cart");
    }
}
