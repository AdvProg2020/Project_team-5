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
import ApProject_OnlineShop.model.productThings.Comment;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CommentsPage extends FxmlController implements Initializable {
    private static long goodId;
    public VBox vbox;
    public GridPane gridpane;
    public ImageView cart;
    private static String pathBack;
    private static String titleBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (getCurrentPerson() instanceof Seller || getCurrentPerson() instanceof Manager) {
            cart.setVisible(false);
        }
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(goodId + "");
//        Good good = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findGoodById", null, inputs)), Good.class);
//        ArrayList<Comment> comments = good.getComments();
        ArrayList<Comment> comments = new Gson().fromJson(connectToServer(new RequestForServer("Good", "getComments", null, inputs)),new TypeToken<ArrayList<Comment>>() {}.getType());
        int size1 = (comments.size() * (110));
        if (size1 > 577) {
            vbox.setPrefWidth(size1 + 20);
        }
        for (Comment comment : comments) {
            GridPane gridPane = new GridPane();
            HBox writer = new HBox();
            writer.setPrefHeight(50);
            writer.setPrefWidth(100);
            writer.setAlignment(Pos.BOTTOM_LEFT);
            Label label = new Label(comment.getPersonString() + " :");
            label.setFont(javafx.scene.text.Font.font("Times New Roman", 14));
            label.setPadding(new javafx.geometry.Insets(10));
            label.setStyle("-fx-font-weight: bold;");
            writer.getChildren().add(label);
            gridPane.add(writer, 0, 0);
            HBox title = new HBox();
            title.setPrefHeight(50);
            title.setPrefWidth(200);
            title.setAlignment(Pos.CENTER);
            Label label2 = new Label(comment.getTitle());
            label2.setFont(javafx.scene.text.Font.font("Bookshelf Symbol 7", 16));
            label2.setPadding(new javafx.geometry.Insets(10));
            title.getChildren().add(label2);
            gridPane.add(title, 1, 0);
            if (comment.isDidCommenterBoughtThisProduct()) {
                HBox buyer = new HBox();
                buyer.setPrefHeight(50);
                buyer.setPrefWidth(100);
                buyer.setAlignment(Pos.CENTER_LEFT);
                Label label3 = new Label("buyer");
                label3.setFont(javafx.scene.text.Font.font("Bookshelf Symbol 7", 12));
                label3.setPadding(new javafx.geometry.Insets(10));
                label3.setTextFill(Color.LIGHTSLATEGREY);
                buyer.getChildren().add(label3);
                gridPane.add(buyer, 0, 1);
            }
            HBox content = new HBox();
            content.setPrefHeight(50);
            content.setPrefWidth(360);
            content.setAlignment(Pos.CENTER_LEFT);
            Label label4 = new Label(comment.getComment());
            label4.setFont(javafx.scene.text.Font.font("Arial", 12));
            label4.setPadding(new javafx.geometry.Insets(10));
            content.getChildren().add(label4);
            gridPane.add(content, 1, 1);
            vbox.getChildren().add(gridPane);
            gridPane.setStyle("-fx-border-color:#8600b3;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-style: solid;" +
                    "-fx-background-color: linear-gradient(to bottom right, #ffb3ff, #ffffff);");
        }
    }

    public void backButton(ActionEvent actionEvent) {
        setScene(pathBack, titleBack);
    }


    public void addComment(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathAfterLogin("addComment.fxml", "add comment");
            LoginController.setPathBack("commentsPage.fxml", "comments");
            setScene("login.fxml", "login");
        } else {
            setScene("addComment.fxml", "add comment");
        }
    }

    public static void setGoodId(long goodId) {
        CommentsPage.goodId = goodId;
    }

    public void accountArea(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathBack("commentsPage.fxml", "comments page");
            LoginController.setPathAfterLogin(null, null);
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("commentsPage.fxml", "comments");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("commentsPage.fxml", "comments");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("commentsPage.fxml", "comments");
            setScene("accountAreaForManager.fxml", "account area");
        }else if (getCurrentPerson() instanceof Supporter){
            AccountAreaForSupporter.setPathBack("commentsPage.fxml", "comments");
            setScene("accountAreaForSupporter.fxml", "account area");
        }
    }

    public static void setPathBack(String pathBack, String titleBack) {
        CommentsPage.pathBack = pathBack;
        CommentsPage.titleBack = titleBack;
    }

    public void cart(MouseEvent mouseEvent) {
        Cart.setPathBack("commentsPage.fxml", "comments page");
        setScene("cart.fxml", "cart");
    }

    public static long getGoodId() {
        return goodId;
    }
}
