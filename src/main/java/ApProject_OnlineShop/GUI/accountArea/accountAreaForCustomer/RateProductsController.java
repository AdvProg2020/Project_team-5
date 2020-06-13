package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.ProductPageRelated.ProductBriefSummery;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RateProductsController extends FxmlController implements Initializable {
    @FXML
    public GridPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//       List<Long> productIds = MainController.getInstance().getAccountAreaForSellerController().viewProducts(sortSelected);
//        int num = 0;
//        int row = 0;
//        for (Long productId : productIds) {
//            if (MainController.getInstance().getAccountAreaForSellerController().isInOff(productId)) {
//                root.add(new ProductBriefSummery().offProductBriefSummery(productId), num % 3, row);
//                num++;
//            }
//            if (!MainController.getInstance().getAccountAreaForSellerController().isInOff(productId)) {
//                root.add(new ProductBriefSummery().getProductForAllProductsPage(productId), num % 3, row);
//                num++;
//            }
//            if (num % 3 == 0)
//                row++;
//        }
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
}
