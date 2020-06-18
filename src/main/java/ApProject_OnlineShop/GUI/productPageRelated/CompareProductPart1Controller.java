package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CompareProductPart1Controller extends FxmlController implements Initializable {
    @FXML
    public GridPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Long> productIds = Shop.getInstance().getAllGoods().stream().map(Good::getGoodId).collect(Collectors.toList());
        productIds.remove(Shop.getInstance().findGoodById(MainController.getInstance().getProductController().getGood().getGoodId()));
        int num = 0;
        int row = 0;
        for (Long productId : productIds) {
            VBox vbox;
            if (Shop.getInstance().getOffGoods().contains(Shop.getInstance().findGoodById(productId))) {
                vbox = new ProductBriefSummery().offProductBriefSummery(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> compare(productId));
            }
            if (!Shop.getInstance().getOffGoods().contains(Shop.getInstance().findGoodById(productId))) {
                vbox = new ProductBriefSummery().getProductForAllProductsPage(productId);
                root.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> compare(productId));
            }
            if (num % 3 == 0)
                row++;
        }
        if (productIds.size() / 3 * 250 > 577) {
            root.setPrefHeight(productIds.size() * 250);
        }
    }

    private void compare(long productId2) {
        if (Shop.getInstance().findGoodById(productId2).getSubCategory().equals(MainController.getInstance().getProductController().getGood().getSubCategory())) {
            setScene("compareTwoProductsInACategory.fxml", "compare");
        } else {
            CompareProductPart2NotInACateogryController.setProductId1(productId2);
            setScene("compareTwoProductsNotInACategory.fxml", "compare");
        }
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("productPage.fxml", "product page");
    }

    public void goToAccountArea(MouseEvent mouseEvent) {
        if (MainController.getInstance().getCurrentPerson() == null) {
            setScene("login.fxml", "login");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
            setScene("accountAreaForManager.fxml", "account area");
        }
    }
}

