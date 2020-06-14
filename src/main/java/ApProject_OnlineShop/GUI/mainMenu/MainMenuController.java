package ApProject_OnlineShop.GUI.mainMenu;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class MainMenuController extends FxmlController {

    public void accountAreaButtonPressed(ActionEvent actionEvent) {
        if (MainController.getInstance().getCurrentPerson() == null) {
            setScene("login.fxml", "Login or Reigster");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
            setScene("accountAreaForManager.fxml", "account area");
        }

    }

    public void productsPageButtonPressed(ActionEvent actionEvent) {
    }

    public void offsPageButtonPressed(ActionEvent actionEvent) {
    }

    public void exitButtonPressed(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Exit", "Exit", "are you sure to exit shop?");
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void cart() {
        setScene("cart.fxml", "cart");
    }
}
