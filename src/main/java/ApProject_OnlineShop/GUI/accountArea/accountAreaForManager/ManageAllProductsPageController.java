package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.productPageRelated.ProductBriefSummery;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Good;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManageAllProductsPageController extends FxmlController implements Initializable {
    @FXML
    public GridPane root;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Button removeButton;

    private Long selectedGoodId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatePage();
    }

    public void onRemovePressed() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "remove", "Remove Product", "are you sure to remove this product?");
        if (result.get() == ButtonType.OK) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeProduct("" + selectedGoodId);
                name.setText("");
                id.setText("");
                updatePage();
                removeButton.setDisable(true);
                SuccessPageFxController.showPage("successful remove", "product removed successfully");
            } catch (Exception e) {
                e.printStackTrace();
                ErrorPageFxController.showPage("error in removing good", e.getMessage());
            }
        }

    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onLogoutIconPressed() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    private void updatePage() {
        root.getChildren().clear();
        List<Long> productIds = Shop.getInstance().getAllGoods().stream().map(Good::getGoodId).collect(Collectors.toList());
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
            VBox productBox = new ProductBriefSummery().getProductForAllProductsPage(productId);
            productBox.setOnMouseClicked(e -> onSelectProduct(productId));
            productBox.setCursor(Cursor.HAND);
            root.add(productBox, num % 3, row);
            num++;
            if (num % 3 == 0)
                row++;
        }
    }

    private void onSelectProduct(Long productId) {
        this.selectedGoodId = productId;
        name.setText(Shop.getInstance().findGoodById(productId).getName());
        id.setText("" + productId);
        removeButton.setDisable(false);
    }
}
