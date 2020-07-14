package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CompareProductPart1Controller extends FxmlController implements Initializable {
    @FXML
    public GridPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> productsIdsString = convertStringToArraylist(connectToServer(new RequestForServer("ProductController", "getAllGoodsIds", null, null)));
        productsIdsString.remove(ProductPage.productId + "");
        List<Long> productIds = new ArrayList<>();
        for (String s : productsIdsString) {
            productIds.add(Long.parseLong(s));
        }
//        List<Long> productIds = Shop.getInstance().getAllGoods().stream().map(Good::getGoodId).collect(Collectors.toList());
//        productIds.remove(Shop.getInstance().findGoodById(MainController.getInstance().getProductController().getGood().getGoodId()).getGoodId());
        int num = 0;
        int row = 0;
        if (productIds.size() % 3 == 0) {
            if (productIds.size() / 3 * 250 > 577) {
                root.setPrefHeight(productIds.size() * 250 / 3);
            }
        } else {
            if (((productIds.size() / 3) + 1) * 250 > 577) {
                root.setPrefHeight(((productIds.size() / 3) + 1) * 250);
            }
        }
        for (Long productId : productIds) {
            VBox vbox;
            if (convertArrayListStringToArrayListLong(convertStringToArraylist
                    (connectToServer(new RequestForServer("ProductController", "getOffGoods", null, null)))).contains(productId)) {
//                if (Shop.getInstance().getOffGoods().contains(Shop.getInstance().findGoodById(productId))) {
                vbox = new ProductBriefSummery().offProductBriefSummery(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> compare(productId));
            }
            if (!convertArrayListStringToArrayListLong(convertStringToArraylist
                    (connectToServer(new RequestForServer("ProductController", "getOffGoods", null, null)))).contains(productId)) {
//            if (!Shop.getInstance().getOffGoods().contains(Shop.getInstance().findGoodById(productId))) {
                vbox = new ProductBriefSummery().getProductForAllProductsPage(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> compare(productId));
            }
            if (num % 3 == 0)
                row++;
        }
    }

    private void compare(long productId2) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId2 + "");
        inputs.add(ProductPage.productId + "");
        if (connectToServer(new RequestForServer("ProductController", "isSubCategoryEquals", null, inputs)).equals("true")) {
            CompareProductPart2InACategoryController.setProductId1(productId2);
            setScene("compareTwoProductsInACategory.fxml", "compare");
        } else {
            CompareProductPart2NotInACategoryController.setProductId1(productId2);
            setScene("compareTwoProductsNotInACategory.fxml", "compare");
        }
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("productPage.fxml", "product page");
    }

    public void goToAccountArea(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathAfterLogin(null, null);
            LoginController.setPathBack("allProductsForCompareProduct.fxml", "All products");
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("allProductsForCompareProduct.fxml", "All products");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("allProductsForCompareProduct.fxml", "All products");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("allProductsForCompareProduct.fxml", "All products");
            setScene("accountAreaForManager.fxml", "account area");
        }
    }
}

