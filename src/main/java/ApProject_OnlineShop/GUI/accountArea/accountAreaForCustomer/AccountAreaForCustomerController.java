package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.accountArea.Styles;
import ApProject_OnlineShop.GUI.productPageRelated.Cart;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.server.RequestForServer;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class AccountAreaForCustomerController extends FxmlController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label credit;
    public ImageView photo;
    private ViewOrders viewOrders = new ViewOrders(this);
    private Styles style = new Styles();
    private static String pathBack;
    private static String titleBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playMusicBackGround(false, false, true);
//        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForCustomerController().getUserPersonalInfo();
        ArrayList<String> personalInfo = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaController", "getUserPersonalInfo", getToken(), null)));
        Image image = new Image(Paths.get("Resources/UserImages/" + getCurrentPerson().getUsername() + ".jpg").toUri().toString());
        File file = new File("Resources\\UserImages\\" + getCurrentPerson().getUsername() + ".jpg");
        if (!file.exists())
            image = new Image(Paths.get("Resources/UserImages/default1.jpg").toUri().toString());
        photo.setImage(image);
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
        playButtonMusic();
        GridPane root = style.makeGridPane();
        Label topic = new Label("Discount codes");
        topic.setFont(Font.font("Times New Roman", 26));
        topic.setPadding(new Insets(5));
        GridPane.setHalignment(topic, HPos.CENTER);
        VBox vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPrefWidth(410);
        scrollPane.setMaxHeight(600);
        scrollPane.setMinHeight(600);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        style.setVBoxStyle(vBox);
        root.add(scrollPane, 1, 2);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + sort);
        List<String> discountCodes = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForCustomerController", "viewDiscountCodes", getToken(), inputs)));
//        List<String> discountCodes = MainController.getInstance().getAccountAreaForCustomerController().viewDiscountCodes(sort);
        if (discountCodes.size() * 50 > 600) {
            vBox.setPrefHeight((discountCodes.size() * 50) + 20);
        } else {
            vBox.setPrefHeight(600);
        }
        for (String discountCode : discountCodes) {
            Hyperlink discountLink = new Hyperlink(discountCode);
            discountLink.setFont(new Font("Times New Roman", 16));
            discountLink.setPrefSize(600, 50);
            discountLink.setOnMouseClicked(e -> viewSingleDiscountCode(discountCode));
            discountLink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            discountLink.setAlignment(Pos.BOTTOM_LEFT);
            discountLink.setPadding(new Insets(8));
            discountLink.setUnderline(false);
            vBox.getChildren().add(discountLink);
        }
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
        playButtonMusic();
        int index = summeryOfDiscountCode.indexOf("  ");
        String code = summeryOfDiscountCode.substring("discount code:".length(), index);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(code);
        DiscountCode discountCode = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findDiscountCode", null, inputs)), DiscountCode.class);
        List<String> discountCodeDetails = discountCode.getAllDetails();
        GridPane root = style.makeGridPane();
        Label discountCodeInfo = new Label("Discount Code Information");
        discountCodeInfo.setFont(Font.font("Times New Roman", 26));
        discountCodeInfo.setPadding(new Insets(13));
        GridPane.setHalignment(discountCodeInfo, HPos.CENTER);
        root.add(discountCodeInfo, 1, 1);
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
        setScene("rateProducts.fxml", "rate products");
    }

    public void backToMain(ActionEvent actionEvent) {
        setScene(pathBack, titleBack);
    }

    public void backToAccountAreaCustomer() {
        setScene("accountAreaForCustomer.fxml", "account area for customer");
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
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void cart(MouseEvent mouseEvent) {
        Cart.setPathBack("accountAreaForCustomer.fxml", "account area");
        setScene("cart.fxml", "cart");
    }

    public static void setPathBack(String pathBack, String titleBack) {
        AccountAreaForCustomerController.pathBack = pathBack;
        AccountAreaForCustomerController.titleBack = titleBack;
    }

    public void increaseCredit() {
        setScene("increaseCredit.fxml", "increase credit");
    }

    public void chatWithSupportersPage(ActionEvent actionEvent) {
        setScene("customerChat.fxml", "customer chat");
    }

    public void onViewAuctionsPressed(ActionEvent actionEvent) {
        setScene("viewAllAuctionsForCustomerPage.fxml", "view auctions");
    }
}
