package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.ProductPageRelated.ProductBriefSummery;
import ApProject_OnlineShop.controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageProduct extends FxmlController implements Initializable {
    public GridPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Long> productIds = MainController.getInstance().getAccountAreaForSellerController().viewProducts(0);
        int num = 0;
        int row = 0;
        for (Long productId : productIds) {
            if (MainController.getInstance().getAccountAreaForSellerController().isInOff(productId)) {
                root.add(new ProductBriefSummery().offProductBriefSummery(productId), num % 3, row);
                num++;
            } if(!MainController.getInstance().getAccountAreaForSellerController().isInOff(productId)) {
                root.add(new ProductBriefSummery().getProductForAllProductsPage(productId), num % 3, row);
                num++;
            }
            if (num % 3 ==0)
                row++;
        }
    }


    public void backButton(ActionEvent actionEvent) {
    }

    public void addProduct(MouseEvent mouseEvent) {
        setScene("addProductForSeller.fxml", "add product");
    }

    public void logout(MouseEvent mouseEvent) {
    }
}
