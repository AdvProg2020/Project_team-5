package ApProject_OnlineShop.GUI.mainMenu;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.GUI.productPageRelated.Cart;
import ApProject_OnlineShop.GUI.productPageRelated.CommentsPage;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController extends FxmlController implements Initializable {
    @FXML
    public GridPane gridpane;

    public void accountAreaButtonPressed(ActionEvent actionEvent) {
        if (MainController.getInstance().getCurrentPerson() == null) {
            LoginController.setPathBack("mainMenuLayout.fxml", "main menu");
            LoginController.setPathAfterLogin(null,null);
            setScene("login.fxml", "Login or Register");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("mainMenuLayout.fxml","main menu");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("mainMenuLayout.fxml","main menu");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("mainMenuLayout.fxml","main menu");
            setScene("accountAreaForManager.fxml", "account area");
        }
    }

    public void productsPageButtonPressed(ActionEvent actionEvent) {
        setScene("allProducts.fxml", "all products");
    }

    public void exitButtonPressed(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Exit", "Exit", "are you sure to exit shop?");
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void cart() {
        Cart.setPathBack("mainMenuLayout.fxml", "main menu");
        setScene("cart.fxml", "cart");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playMusicForMainLayout(true);
        if (MainController.getInstance().getCurrentPerson() instanceof Customer || MainController.getInstance().getCurrentPerson() == null) {
            ImageView imageView = new ImageView(new Image("/pictures/shoppingBag.png"));
            gridpane.add(imageView, 2, 0);
            imageView.setFitHeight(200);
            imageView.setFitHeight(100);
            imageView.setCursor(Cursor.HAND);
            imageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            GridPane.setHalignment(imageView, HPos.LEFT);
            GridPane.setValignment(imageView, VPos.CENTER);
            imageView.setOnMouseClicked(e -> cart());
        }
    }

}
