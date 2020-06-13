package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;


import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class RateProductsPart2Controller extends FxmlController {

    private static long productIdForRate;
    private static Stage window;


    private void rateProduct(long productId2) {
        productIdForRate = productId2;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Rate");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(SuccessPageFxController.class.getClassLoader().getResource("ratePage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 400);
        window.setScene(scene);
        window.showAndWait();
    }


    public void rate10(MouseEvent mouseEvent) {
        rateProductFinally(10);
    }

    public void rate2(MouseEvent mouseEvent) {
        rateProductFinally(2);
    }

    public void rate4(MouseEvent mouseEvent) {
        rateProductFinally(4);
    }

    public void rate6(MouseEvent mouseEvent) {
        rateProductFinally(6);
    }

    public void rate8(MouseEvent mouseEvent) {
        rateProductFinally(8);
    }

    private void rateProductFinally(int rate) {
        try {
            MainController.getInstance().getAccountAreaForCustomerController().rateProduct(productIdForRate, rate);
            SuccessPageFxController.showPage("successful rate", "your rate added successfully");
            window.close();
        } catch (IOException e) {
            ErrorPageFxController.showPage("error occured during rate", e.getMessage());
        } catch (FileCantBeSavedException e) {
            ErrorPageFxController.showPage("error occured during rate", e.getMessage());
        }
    }

    public static void setProductIdForRate(long productIdForRate) {
        RateProductsPart2Controller.productIdForRate = productIdForRate;
    }

    public static void setWindow(Stage window) {
        RateProductsPart2Controller.window = window;
    }
}
