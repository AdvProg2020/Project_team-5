package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.RateProductsPart2Controller;
import ApProject_OnlineShop.GUI.productPageRelated.CommentsPage;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.productExceptions.ProductNotFoundExceptionForSeller;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class ProductPageControllerForSeller extends FxmlController implements Initializable {
    private static long productId = 0;
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
        setScene("manageProductsForSeller.fxml", "manage products");
    }

    public void logout(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.setImage(new Image(Paths.get("Resources/productImages/" + productId + ".jpg").toUri().toString()));
//        List<String> mainInfo = MainController.getInstance().getProductController().getMainInfo(productId);
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
            sellers.getChildren().add(sellerHBox);
        }
    }

    public static void setProductId(long productId) {
        ProductPageControllerForSeller.productId = productId;
        MainController.getInstance().getProductController().setGoodById(productId);
    }

    public void removeProduct(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Remove", "Remove product", "are you sure you want to remove this?");
        if (result.get() == ButtonType.OK) {
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add("" + productId);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "removeProduct", getToken(), inputs));
            if (serverResponse.equals("product removed successfully")) {
                setScene("manageProductsForSeller.fxml", "manage products");
            } else {
                ErrorPageFxController.showPage("can not remove this product", serverResponse);
            }
//            try {
//                MainController.getInstance().getAccountAreaForSellerController().removeProduct(productId);
//                setScene("manageProductsForSeller.fxml", "manage products");
//            } catch (ProductNotFoundExceptionForSeller e) {
//                ErrorPageFxController.showPage("can not remove this product", e.getMessage());
//            } catch (IOException e) {
//                ErrorPageFxController.showPage("can not remove this product", e.getMessage());
//            } catch (FileCantBeSavedException e) {
//                ErrorPageFxController.showPage("can not remove this product", e.getMessage());
//            } catch (FileCantBeDeletedException e) {
//                ErrorPageFxController.showPage("can not remove this product", e.getMessage());
//            }
        }
    }

    public void viewBuyers(ActionEvent actionEvent) {
        ViewBuyersOfGoodController.setProductId(productId);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("buyers");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(SuccessPageFxController.class.getClassLoader().getResource("viewBuyersOfGoodForSeller.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 600);
        window.setScene(scene);
        ViewBuyersOfGoodController.setStage(window);
        window.showAndWait();
    }

    public void editField(ActionEvent actionEvent) {
        EditPorductController.setGoodId(productId);
        setScene("editProduct.fxml", "edit product");
    }

    public void showComments(ActionEvent actionEvent) {
        CommentsPage.setGoodId(productId);
        CommentsPage.setPathBack("productPageEditableForSeller.fxml", "product page for seller");
        setScene("commentsPage.fxml", "comments");
    }
}
