package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Comment;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CommentsPage extends FxmlController implements Initializable {
    private static long goodId;
    public VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Comment> comments = Shop.getInstance().findGoodById(goodId).getComments();
        for (Comment comment : comments) {
            GridPane gridPane = new GridPane();
            HBox writer = new HBox();
            writer.setPrefHeight(50);
            writer.setPrefWidth(100);
            writer.setAlignment(Pos.BOTTOM_LEFT);
            Label label = new Label(comment.getPerson().getUsername() + " :");
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
        setScene("productPageEditableForSeller.fxml", "product page");
    }


    public void addComment(MouseEvent mouseEvent) {
        setScene("addComment.fxml", "add comment");
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

    public static void setGoodId(long goodId) {
        CommentsPage.goodId = goodId;
    }
}
