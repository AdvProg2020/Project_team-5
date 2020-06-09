package ApProject_OnlineShop.GUI.mainMenu;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class MainMenuController extends FxmlController {


    public void accountAreaButtonPressed(ActionEvent actionEvent) {
        if (MainController.getInstance().getCurrentPerson() == null) {
            setScene("login.fxml","Login or Reigster");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {

        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {

        } else if (MainController.getInstance().getCurrentPerson() instanceof Manager){

        }
    }

    public void productsPageButtonPressed(ActionEvent actionEvent) {
    }

    public void offsPageButtonPressed(ActionEvent actionEvent) {
    }


    public void exitButtonPressed(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
