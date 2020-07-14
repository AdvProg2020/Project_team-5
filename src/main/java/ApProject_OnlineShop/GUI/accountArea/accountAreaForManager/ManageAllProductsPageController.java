package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.productPageRelated.ProductBriefSummery;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.GUI.productPageRelated.ProductPage;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
//            try {
//                MainController.getInstance().getAccountAreaForManagerController().removeProduct("" + selectedGoodId);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add("" + selectedGoodId);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "removeProduct", getToken(), inputs));
            if (serverResponse.equals("product removed successfully")) {
                name.setText("");
                id.setText("");
                updatePage();
                removeButton.setDisable(true);
                SuccessPageFxController.showPage("successful remove", "product removed successfully");
            } else {
                ErrorPageFxController.showPage("error for removing", serverResponse);
            }
//            } catch (Exception e) {
//                e.printStackTrace();
//                ErrorPageFxController.showPage("error in removing good", e.getMessage());
//            }
        }

    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onLogoutIconPressed() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
        }
    }

    private void updatePage() {
        root.getChildren().clear();
        List<String> productsIdsString = convertStringToArraylist(connectToServer(new RequestForServer("ProductController", "getAllGoodsIds", null, null)));
        productsIdsString.remove(ProductPage.productId + "");
        List<Long> productIds = new ArrayList<>();
        for (String s : productsIdsString) {
            productIds.add(Long.parseLong(s));
        }
//        List<Long> productIds = Shop.getInstance().getAllGoods().stream().map(Good::getGoodId).collect(Collectors.toList());
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
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productId + "");
        Good good = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findGoodById", null, inputs)), Good.class);
        name.setText(good.getName());
        id.setText("" + productId);
        removeButton.setDisable(false);
    }
}
