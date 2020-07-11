package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.GUI.productPageRelated.ProductBriefSummery;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageProduct extends FxmlController implements Initializable {
    @FXML
    public GridPane root;
    private static int sortSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + sortSelected);
        ArrayList<String> productIdsString = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "viewProducts", getToken(), inputs)));
        List<Long> productIds = new ArrayList<>();
        for (String s : productIdsString) {
            productIds.add(Long.parseLong(s));
        }
//        List<Long> productIds = MainController.getInstance().getAccountAreaForSellerController().viewProducts(sortSelected);
        int num = 0;
        int row = 0;
        int size1;
        if (productIds.size() % 3 == 0) {
            size1 = productIds.size() * 250 / 3;
        } else {
            size1 = ((productIds.size() / 3) + 1) * 250;
        }
        if (size1 > 577) {
            root.setPrefHeight(size1);
        }
        for (Long productId : productIds) {
            ArrayList<String> inputs1 = new ArrayList<>();
            inputs1.add(productId + "");
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "isInOff", getToken(), inputs1));
            if (serverResponse.equals("true")) {
                VBox vbox = new ProductBriefSummery().offProductBriefSummery(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> showProduct(productId));
            }
            if (serverResponse.equals("false")) {
                VBox vbox = new ProductBriefSummery().getProductForAllProductsPage(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> showProduct(productId));
            }
            if (num % 3 == 0)
                row++;
        }
    }

    private void showProduct(Long productId) {
        ProductPageControllerForSeller.setProductId(productId);
        setScene("productPageEditableForSeller.fxml", "product page");
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("accountAreaForSeller.fxml", "account area");
    }

    public void addProduct(MouseEvent mouseEvent) {
        setScene("addProductForSeller.fxml", "add product");
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

    public void sortByVisitNumber(ActionEvent actionEvent) {
        sortSelected = 1;
        setScene("manageProductsForSeller.fxml", "manage products");
    }

    public void sortByAverageRate(ActionEvent actionEvent) {
        sortSelected = 2;
        setScene("manageProductsForSeller.fxml", "manage products");
    }

    public void sortByModificationDate(ActionEvent actionEvent) {
        sortSelected = 3;
        setScene("manageProductsForSeller.fxml", "manage products");
    }

    public void sortByAvailableNumber(ActionEvent actionEvent) {
        sortSelected = 5;
        setScene("manageProductsForSeller.fxml", "manage products");
    }

    public void sortByPrice(ActionEvent actionEvent) {
        sortSelected = 4;
        setScene("manageProductsForSeller.fxml", "manage products");
    }
}
