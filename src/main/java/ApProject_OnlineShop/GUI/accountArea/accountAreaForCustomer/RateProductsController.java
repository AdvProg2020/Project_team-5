package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.ProductPageRelated.ProductBriefSummery;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RateProductsController extends FxmlController implements Initializable {
    @FXML
    public GridPane root;
    private static long productIdForRate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Long> productIds = MainController.getInstance().getAccountAreaForCustomerController().getBoughtProducts();
        int num = 0;
        int row = 0;
        for (Long productId : productIds) {
            VBox vbox = null;
            if (MainController.getInstance().getAccountAreaForSellerController().isInOff(productId)) {
                vbox = new ProductBriefSummery().offProductBriefSummery(productId);
                vbox.setOnMouseClicked(e -> rateProduct(productId));
            }
            if (!MainController.getInstance().getAccountAreaForSellerController().isInOff(productId)) {
                vbox = new ProductBriefSummery().getProductForAllProductsPage(productId);
                vbox.setOnMouseClicked(e -> rateProduct(productId));
            }
            root.add(vbox, num % 3, row);
            num++;
            vbox.setCursor(Cursor.HAND);
            if (num % 3 == 0)
                row++;
        }
    }


    private void rateProduct(long productId2) {
        productIdForRate = productId2;
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Rate");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(SuccessPageFxController.class.getClassLoader().getResource("successPage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 400);
        window.setScene(scene);
        window.showAndWait();
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("accountAreaForCustomer.fxml", "account area");
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


    public void rate10(MouseEvent mouseEvent) {
        try {
            MainController.getInstance().getAccountAreaForCustomerController().rateProduct(productIdForRate, 10);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }

}
