package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSupporter.AccountAreaForSupporter;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.persons.Supporter;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CompareProductPart2InACategoryController extends FxmlController implements Initializable {
    private static long productId1;
    private long productId2;
    public ImageView image1, image2;
    public GridPane gridpane;
    public VBox vbox1, vbox2;
    public Label name1, name2, brand1, brand2, subCategory1, subCategory2, date1, date2, seenNumber1, seenNumber2, sellerNumber1, sellerNumber2, price1, price2;
    public HBox rate1, rate2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        productId2 = MainController.getInstance().getProductController().getGood().getGoodId();
        productId2 = ProductPage.productId;
//        image1.setImage(new Image(Paths.get("Resources/productImages/" + productId1 + ".jpg").toUri().toString()));
//        image2.setImage(new Image(Paths.get("Resources/productImages/" + productId2 + ".jpg").toUri().toString()));
        ArrayList<String> input33 = new ArrayList<>();
        input33.add("" + productId1);
        RequestForServer requestForServer = new RequestForServer("ProductController", "getProductImage", getToken(), input33);
        image1.setImage(new Image(new ByteArrayInputStream(connectToServerBytes(requestForServer))));
        ArrayList<String> input32 = new ArrayList<>();
        input32.add("" + productId2);
        RequestForServer requestForServer2 = new RequestForServer("ProductController", "getProductImage", getToken(), input32);
        image2.setImage(new Image(new ByteArrayInputStream(connectToServerBytes(requestForServer2))));
//        ArrayList<String> details = MainController.getInstance().getProductController().compareWithAnotherProductGUI(productId2,productId1);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId2 + "");
        inputs.add(productId1 + "");
        ArrayList<String> details = convertStringToArraylist(connectToServer(new RequestForServer("ProductController", "compareWithAnotherProductGUI", null, inputs)));
        name1.setText(details.get(0));
        name2.setText(details.get(1));
        brand1.setText(details.get(2));
        brand2.setText(details.get(3));
        double num = Double.parseDouble(details.get(4));
        for (int i = 0; i < num / 2; i++) {
            ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
            star.setFitWidth(20);
            star.setFitHeight(20);
            rate1.getChildren().add(star);
        }
        Label rateDouble = new Label("  " + (Double.parseDouble(details.get(4)) / 2));
        setLabelStyle(rateDouble);
        rate1.getChildren().add(rateDouble);
        for (int i = 0; i < (int) (Double.parseDouble(details.get(5)) / 2); i++) {
            ImageView star = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/star.png").toString()));
            star.setFitWidth(20);
            star.setFitHeight(20);
            rate2.getChildren().add(star);
        }
        Label rateDouble2 = new Label("  " + (Double.parseDouble(details.get(5)) / 2));
        setLabelStyle(rateDouble2);
        rate2.getChildren().add(rateDouble2);
        subCategory1.setText(details.get(6));
        subCategory2.setText(details.get(7));
        date1.setText(details.get(8));
        date2.setText(details.get(9));
        seenNumber1.setText(details.get(10));
        seenNumber2.setText(details.get(11));
        sellerNumber1.setText(details.get(12));
        sellerNumber2.setText(details.get(13));
        price1.setText(details.get(14) + " Rials");
        price2.setText(details.get(15) + " Rials");
        setLabelStyle(name1);
        setLabelStyle(name2);
        setLabelStyle(brand1);
        setLabelStyle(brand2);
        setLabelStyle(subCategory1);
        setLabelStyle(subCategory2);
        setLabelStyle(date1);
        setLabelStyle(date2);
        setLabelStyle(seenNumber1);
        setLabelStyle(seenNumber2);
        setLabelStyle(sellerNumber1);
        setLabelStyle(sellerNumber2);
        setLabelStyle(price1);
        setLabelStyle(price2);
        addCategoryProperties();
    }

    private void addCategoryProperties() {
        ArrayList<String> inputs22 = new ArrayList<>();
        inputs22.add(productId2 + "");
        Good good2 = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findGoodById", null, inputs22)), Good.class);
        ArrayList<String> inputs23 = new ArrayList<>();
        inputs23.add(productId1 + "");
        Good good1 = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findGoodById", null, inputs23)), Good.class);
        HashMap<String, String> categoryPropertiesGood2 = new HashMap<>(good2.getCategoryProperties());
        HashMap<String, String> categoryPropertiesGood1 = new HashMap<>(good1.getCategoryProperties());
        if ((categoryPropertiesGood1.keySet().size() + 8) * 50 > 425) {
            gridpane.setPrefHeight((categoryPropertiesGood1.keySet().size() + 8) * 50);
            vbox1.setPrefHeight((categoryPropertiesGood1.keySet().size() + 8) * 50);
            vbox2.setPrefHeight((categoryPropertiesGood1.keySet().size() + 8) * 50);
        }
        for (String categoryProperties : categoryPropertiesGood1.keySet()) {
            HBox hBox = new HBox();
            hBox.setPrefWidth(300);
            hBox.setPrefHeight(50);
            hBox.setAlignment(Pos.CENTER_LEFT);
            Label property = new Label(categoryProperties + " :");
            property.setPrefHeight(52);
            property.setPrefWidth(170);
            property.setPadding(new Insets(10));
            property.setTextFill(Color.rgb(18, 48, 148));
            property.setAlignment(Pos.CENTER_LEFT);
            property.setFont(new Font(18));
            hBox.getChildren().add(property);
            Label propertyValue = new Label(categoryPropertiesGood1.get(categoryProperties));
            propertyValue.setPrefHeight(50);
            propertyValue.setPrefWidth(128);
            propertyValue.setAlignment(Pos.CENTER);
            setLabelStyle(propertyValue);
            hBox.getChildren().add(propertyValue);
            vbox1.getChildren().add(hBox);
        }
        for (String categoryProperties : categoryPropertiesGood2.keySet()) {
            HBox hBox = new HBox();
            hBox.setPrefWidth(300);
            hBox.setPrefHeight(50);
            hBox.setAlignment(Pos.CENTER_LEFT);
            Label property = new Label(categoryProperties + " :");
            property.setPrefHeight(52);
            property.setPrefWidth(170);
            property.setPadding(new Insets(10));
            property.setTextFill(Color.rgb(18, 48, 148));
            property.setAlignment(Pos.CENTER_LEFT);
            property.setFont(new Font(18));
            hBox.getChildren().add(property);
            Label propertyValue = new Label(categoryPropertiesGood2.get(categoryProperties));
            propertyValue.setPrefHeight(50);
            propertyValue.setPrefWidth(128);
            propertyValue.setAlignment(Pos.CENTER);
            setLabelStyle(propertyValue);
            hBox.getChildren().add(propertyValue);
            vbox2.getChildren().add(hBox);
        }
    }

    public void goToAccountArea(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathAfterLogin(null, null);
            LoginController.setPathBack("compareTwoProductsInACategory.fxml", "compare products");
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("compareTwoProductsInACategory.fxml", "compare products");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("compareTwoProductsInACategory.fxml", "compare products");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("compareTwoProductsInACategory.fxml", "compare products");
            setScene("accountAreaForManager.fxml", "account area");
        } else if (getCurrentPerson() instanceof Supporter) {
            AccountAreaForSupporter.setPathBack("compareTwoProductsInACategory.fxml", "compare products");
            setScene("accountAreaForSupporter.fxml", "account area");
        }
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("allProductsForCompareProduct.fxml", "compare products");
    }

    public static long getProductId1() {
        return productId1;
    }

    public static void setProductId1(long productId1) {
        CompareProductPart2InACategoryController.productId1 = productId1;
    }

    private void setLabelStyle(Label label) {
        label.setFont(Font.font("Times New Roman", 14));
    }
}
