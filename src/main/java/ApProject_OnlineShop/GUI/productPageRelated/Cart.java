package ApProject_OnlineShop.GUI.ProductPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.productThings.GoodInCart;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Cart extends FxmlController implements Initializable {
    public VBox items;
    private static String pathBack;
    private static String titleBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        List<Long> productIds = MainController.getInstance().getAccountAreaForCustomerController().viewInCartProducts();
        ArrayList<String> inputs11 = new ArrayList<>();
        inputs11.add(getId() + "");
        List<Long> productIds = convertArrayListStringToArrayListLong(convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForCustomerController", "viewInCartProducts", null, inputs11))));
        if (productIds == null || productIds.size() == 0) {
            Label isEmpty = new Label("cart is empty!");
            isEmpty.setPadding(new Insets(15, 15, 0, 15));
            isEmpty.setFont(Font.font("Times New Roman", 20));
            items.getChildren().add(isEmpty);
        }
        for (Long productId : productIds) {
            HBox productBox = new HBox();
            setHBoxStyle(productBox);
//            ImageView imageView = new ImageView(new Image(Paths.get("Resources/productImages/" + productId + ".jpg").toUri().toString()));
            ArrayList<String> input33 = new ArrayList<>();
            input33.add("" + productId);
            RequestForServer requestForServer = new RequestForServer("ProductController", "getProductImage", getToken(), input33);
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(connectToServerBytes(requestForServer))));
            imageView.setFitWidth(149);
            imageView.setFitHeight(149);
            productBox.getChildren().add(imageView);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add("" + productId);
            inputs.add("" + getId());
            List<String> goodInfo = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForCustomerController", "viewGoodInCartById", getToken(), inputs)));
//            List<String> goodInfo = MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(productId);
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
            minus.setCursor(Cursor.HAND);
            minus.setOnMouseClicked(e -> decreaseProduct(productId));
            numberBox.getChildren().add(minus);
            hBox.getChildren().add(numberBox);
            HBox priceBox = new HBox();
            priceBox.setAlignment(Pos.CENTER);
            Label price = new Label(goodInfo.get(3) + " Rials");
            price.setFont(Font.font("Times New Roman", 14));
            price.setPadding(new Insets(0, 15, 0, 15));
            priceBox.getChildren().add(price);
            hBox.getChildren().add(priceBox);
            HBox totalPriceBox = new HBox();
            totalPriceBox.setAlignment(Pos.CENTER_RIGHT);
            Label totalPrice = new Label(goodInfo.get(4) + " Rials");
            totalPrice.setFont(Font.font("Times New Roman", 14));
            totalPrice.setPadding(new Insets(0, 15, 0, 15));
            totalPriceBox.getChildren().add(totalPrice);
            hBox.getChildren().add(totalPriceBox);
            textFieldVBox.getChildren().add(hBox);
            items.getChildren().add(productBox);
        }
        if (productIds.size() > 0) {
            HBox priceBox = new HBox();
            priceBox.setAlignment(Pos.CENTER);
            Label text = new Label("total price is");
            text.setFont(Font.font("Times New Roman", 16));
            priceBox.getChildren().add(text);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            List<GoodInCart> cart = new Gson().fromJson(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getCart", null, inputs)), new TypeToken<List<GoodInCart>>() {
            }.getType());
            ArrayList<String> input2 = new ArrayList<>();
            input2.add(new Gson().toJson(cart));
            long totalPrice3 = Long.parseLong(connectToServer(new RequestForServer("AccountAreaForCustomerController", "finalPriceOfAList", null, input2)));
            Label finalPrice = new Label(" " + totalPrice3 + " Rials");
            finalPrice.setFont(Font.font("Times New Roman", 16));
            finalPrice.setTextFill(Color.RED);
            HBox.setMargin(finalPrice, new Insets(8, 8, 8, 8));
            priceBox.getChildren().add(finalPrice);
            items.getChildren().add(priceBox);
        }
        if (150 * productIds.size() > 540) {
            items.setPrefHeight(150 * productIds.size() + 50);
        }
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
//        MainController.getInstance().getAccountAreaForCustomerController().decreaseInCartProduct(productId);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        inputs.add(getId() + "");
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForCustomerController", "decreaseInCartProduct", null, inputs));
        setScene("cart.fxml", "cart");
    }

    public void increaseProduct(long productId) {
//        try {
//            MainController.getInstance().getAccountAreaForCustomerController().increaseInCartProduct(productId);
//        } catch (Exception e) {
//            ErrorPageFxController.showPage("Error happened", e.getMessage());
//        }
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        inputs.add(getId() + "");
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForCustomerController", "increaseInCartProduct", null, inputs));
        if (!serverResponse.equals("Successfully increased")) {
            ErrorPageFxController.showPage("error", serverResponse);
        }
        setScene("cart.fxml", "cart");
    }

    public void purchase() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(getId() + "");
        ArrayList<GoodInCart> cart = new Gson().fromJson(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getCart", null, inputs)), new TypeToken<List<GoodInCart>>() {
        }.getType());
        if (cart.size() == 0) {
            ErrorPageFxController.showPage("error for purchase", "your cart is empty");
            return;
        }
        if (getCurrentPerson() instanceof Customer) {
            setScene("purchasePage1.fxml", "purchase");
        } else if (getCurrentPerson() == null) {
            LoginController.setPathAfterLogin("purchasePage1.fxml", "purchase");
            LoginController.setPathBack("cart.fxml", "cart");
            setScene("login.fxml", "login");
        }
    }

    public void showProduct(long productId) {
        ProductPage.setProductId(productId);
        ProductPage.setPathBack("cart.fxml", "cart");
        setScene("productPage.fxml", "productPage");
    }

    public void backPressed(MouseEvent mouseEvent) {
        setScene(pathBack, titleBack);
    }

    public static void setPathBack(String pathBack, String titleBack) {
        Cart.pathBack = pathBack;
        Cart.titleBack = titleBack;
    }
}
