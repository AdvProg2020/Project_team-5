package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.accountArea.Styles;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.*;

public class AccountAreaForCustomerController extends FxmlController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label credit;
    private ViewOrders viewOrders = new ViewOrders(this);
    private Styles style = new Styles();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForCustomerController().getUserPersonalInfo();
        userName.setText(personalInfo.get(0));
        name.setText(personalInfo.get(1));
        lastName.setText(personalInfo.get(2));
        email.setText(personalInfo.get(3));
        phoneNumber.setText(personalInfo.get(4));
        credit.setText(personalInfo.get(5));
    }

    public void viewDiscountCode() {
        viewSortedDiscountCode(0);
    }

    public void viewSortedDiscountCode(int sort) {
        GridPane root = style.makeGridPane();
        Label topic = new Label("Discount codes");
        topic.setFont(Font.font("Times New Roman", 26));
        topic.setPadding(new Insets(5));
        GridPane.setHalignment(topic, HPos.CENTER);
        VBox vBox = new VBox();
        style.setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        List<String> discountCodes = MainController.getInstance().getAccountAreaForCustomerController().viewDiscountCodes(sort);
        for (String discountCode : discountCodes) {
            Hyperlink discountLink = new Hyperlink(discountCode);
            discountLink.setFont(new Font("Times New Roman",16));
            discountLink.setOnMouseClicked(e -> viewSingleDiscountCode(discountCode));
            discountLink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            discountLink.setAlignment(Pos.BOTTOM_LEFT);
            discountLink.setPadding(new Insets(8));
            discountLink.setUnderline(false);
            vBox.getChildren().add(discountLink);
        }
        Image shoppingBag = new Image(getClass().getClassLoader().getResource("shoppingBag.png").toString());
        ImageView shoppingBagImageView = new ImageView(shoppingBag);
        shoppingBagImageView.setFitHeight(80);
        shoppingBagImageView.setFitWidth(60);
        shoppingBagImageView.setCursor(Cursor.HAND);
        root.add(shoppingBagImageView,2,2);
        GridPane.setValignment(shoppingBagImageView,VPos.TOP);
        root.add(topic, 1, 1);
        MenuItem sortByDiscountPercent = new MenuItem("sort by discount percent");
        MenuItem sortByEndDate = new MenuItem("sort by end date");
        MenuItem sortByMaxDiscount = new MenuItem("sort by maximum discount amount");
        sortByDiscountPercent.setOnAction(e -> viewSortedDiscountCode(1));
        sortByEndDate.setOnAction(e -> viewSortedDiscountCode(2));
        sortByMaxDiscount.setOnAction(e -> viewSortedDiscountCode(3));
        style.setMenuItems(sortByDiscountPercent);
        style.setMenuItems(sortByEndDate);
        style.setMenuItems(sortByMaxDiscount);
        MenuButton sorts = new MenuButton("sorts", null, sortByDiscountPercent, sortByEndDate, sortByMaxDiscount);
        style.setMenuButtonStyle(sorts);
        root.getChildren().get(0).setOnMouseClicked(e -> backToAccountAreaCustomer());
        root.add(sorts, 0, 2);
        StageController.setSceneJavaFx(root);
    }

    public void viewSingleDiscountCode(String summeryOfDiscountCode) {
        int index = summeryOfDiscountCode.indexOf("  ");
        String code = summeryOfDiscountCode.substring("discount code:".length(), index);
        List<String> discountCodeDetails = Shop.getInstance().findDiscountCode(code).getAllDetails();
        GridPane root = style.makeGridPane();
        Label discountCodeInfo = new Label("Discount Code Information");
        discountCodeInfo.setFont(Font.font("Times New Roman", 26));
        discountCodeInfo.setPadding(new Insets(7));
        GridPane.setHalignment(discountCodeInfo, HPos.CENTER);
        root.add(discountCodeInfo, 1, 1);
        Image shoppingBag = new Image(getClass().getClassLoader().getResource("shoppingBag.png").toString());
        ImageView shoppingBagImageView = new ImageView(shoppingBag);
        shoppingBagImageView.setFitHeight(80);
        shoppingBagImageView.setFitWidth(60);
        shoppingBagImageView.setCursor(Cursor.HAND);
        root.add(shoppingBagImageView,2,2);
        GridPane.setValignment(shoppingBagImageView,VPos.TOP);
        VBox vBox = new VBox();
        addDiscountDetailsToVBox(discountCodeDetails, vBox);
        style.setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        vBox.setMinHeight(400);
        vBox.setMinWidth(600);
        vBox.setSpacing(20);
        root.getChildren().get(0).setOnMouseClicked(e -> viewDiscountCode());
        StageController.setSceneJavaFx(root);
    }

    public void showOrders() {
        viewOrders.viewSortedOrders(0);
    }

    public void rateProducts() {

    }


    public void backToMain(ActionEvent actionEvent) {
        setScene("mainMenuLayout.fxml", "main menu");
    }

    public void backToAccountAreaCustomer() {
        setScene("accountAreaForCustomer.fxml", "main menu");
    }

    public void addDiscountDetailsToVBox(List<String> discountCodeDetails, VBox vBox) {
        Label codeLabel = new Label("discount code:     " + discountCodeDetails.get(0));
        Label startDate = new Label("start date:     " + discountCodeDetails.get(1));
        Label endDate = new Label("end date:     " + discountCodeDetails.get(2));
        Label discountPercent = new Label("discount percent:     " + discountCodeDetails.get(3));
        Label maxDiscountAmount = new Label("maximum discount amount:     " + discountCodeDetails.get(4));
        style.setTextFont(codeLabel);
        style.setTextFont(startDate);
        style.setTextFont(endDate);
        style.setTextFont(discountPercent);
        style.setTextFont(maxDiscountAmount);
        vBox.getChildren().add(codeLabel);
        vBox.getChildren().add(startDate);
        vBox.getChildren().add(endDate);
        vBox.getChildren().add(discountPercent);
        vBox.getChildren().add(maxDiscountAmount);
    }



    public void editField(ActionEvent actionEvent) {
        setScene("editFieldPersons.fxml", "edit field");
    }

    public void logout() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }
}
