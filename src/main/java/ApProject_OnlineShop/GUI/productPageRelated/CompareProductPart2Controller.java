package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class CompareProductPart2Controller extends FxmlController implements Initializable {
    private static long productId1;
    private long productId2;
    public ImageView image1, image2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productId2 = MainController.getInstance().getProductController().getGood().getGoodId();
        image1.setImage(new Image(Paths.get("Resources/productImages/" + productId1 + ".jpg").toUri().toString()));
        image2.setImage(new Image(Paths.get("Resources/productImages/" + productId2 + ".jpg").toUri().toString()));
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

    public void backButton(ActionEvent actionEvent) {
        setScene("allProductsForCompareProduct.fxml", "compare products");
    }

    public static long getProductId1() {
        return productId1;
    }

    public static void setProductId1(long productId1) {
        CompareProductPart2Controller.productId1 = productId1;
    }
}
