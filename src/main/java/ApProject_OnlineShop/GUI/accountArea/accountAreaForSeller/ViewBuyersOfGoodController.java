package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewBuyersOfGoodController extends FxmlController implements Initializable {
    private static long productId;
    private static Stage stage;
    @FXML
    public VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + productId);
        ArrayList<String> buyers = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "buyersOfProduct", getToken(), inputs)));
//        ArrayList<String> buyers = null;
//        try {
//            buyers = MainController.getInstance().getAccountAreaForSellerController().buyersOfProduct(productId);
//        } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
//            productNotFoundExceptionForSeller.printStackTrace();
//        }
        for (String buyer : buyers) {
            HBox details = new HBox();
            details.setAlignment(Pos.CENTER_LEFT);
            VBox vbox1 = new VBox();
            vbox1.setAlignment(Pos.CENTER_LEFT);
            Label detailKey1 = new Label("- " + buyer);
            detailKey1.setFont(Font.font("Times New Roman", 16));
            detailKey1.setPadding(new Insets(15));
            vbox1.getChildren().add(detailKey1);
            details.getChildren().add(vbox1);
            vbox.getChildren().add(details);
        }
        if (buyers.size() == 0) {
            HBox details = new HBox();
            details.setAlignment(Pos.CENTER_LEFT);
            VBox vbox1 = new VBox();
            vbox1.setAlignment(Pos.CENTER_LEFT);
            Label detailKey1 = new Label("- " + "no one bought this!");
            detailKey1.setFont(Font.font("Times New Roman", 16));
            detailKey1.setPadding(new Insets(15));
            vbox1.getChildren().add(detailKey1);
            details.getChildren().add(vbox1);
            vbox.getChildren().add(details);
        }
    }

    public static void setProductId(long productId) {
        ViewBuyersOfGoodController.productId = productId;
    }

    public void close(ActionEvent actionEvent) {
        stage.close();
    }

    public static void setStage(Stage stage) {
        ViewBuyersOfGoodController.stage = stage;
    }
}
