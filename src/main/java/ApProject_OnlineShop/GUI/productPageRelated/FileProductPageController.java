package ApProject_OnlineShop.GUI.ProductPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSupporter.AccountAreaForSupporter;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.persons.Supporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FileProductPageController extends FxmlController implements Initializable {
    public static long productId = 0;

    @FXML
    public ImageView image;
    @FXML
    public Label name;
    @FXML
    public Label subcategory;
    @FXML
    public HBox rate;
    @FXML
    public Label downloadNumber;
    @FXML
    public Label details;
    @FXML
    public VBox sellers;
    @FXML
    public Label price;
    @FXML
    public VBox properties;
    @FXML
    public Label detailsLabel;
    @FXML
    public ImageView cart;

    private static String pathBack;
    private static String titleBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void backButton(ActionEvent actionEvent) {
        setScene(pathBack, titleBack);
    }

    public static void setProductId(long productId) {
        FileProductPageController.productId = productId;
//        MainController.getInstance().getProductController().setGoodById(productId);
    }

    public void goToAccountArea(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathAfterLogin(null, null);
            LoginController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForManager.fxml", "account area");
        } else if (getCurrentPerson() instanceof Supporter) {
            AccountAreaForSupporter.setPathBack("fileProductPage.fxml", "file product page");
            setScene("accountAreaForSupporter.fxml", "account area");
        }
    }

    public static void setPathBack(String pathBack, String titleBack) {
        FileProductPageController.pathBack = pathBack;
        FileProductPageController.titleBack = titleBack;
    }

    public void cart(MouseEvent mouseEvent) {
        Cart.setPathBack("fileProductPage.fxml", "file product page");
        setScene("cart.fxml", "cart");
    }
}
