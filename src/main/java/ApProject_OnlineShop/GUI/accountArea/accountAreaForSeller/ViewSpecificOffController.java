package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewSpecificOffController extends FxmlController implements Initializable {
    private static Long offId;
    public Label id, startDate, endDate, maxDiscount, discountPercent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + offId);
        ArrayList<String> offDetails = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "viewOffGUI", getToken(), inputs)));
//        ArrayList<String> offDetails = MainController.getInstance().getAccountAreaForSellerController().viewOffGUI(offId);
        id.setText(offDetails.get(0));
        startDate.setText(offDetails.get(1));
        endDate.setText(offDetails.get(2));
        maxDiscount.setText(offDetails.get(3));
        discountPercent.setText(offDetails.get(4));
    }

    public static void setOffId(Long offId) {
        ViewSpecificOffController.offId = offId;
    }

    public void logout(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("viewOffsForSeller.fxml", "offs");
    }

    public void editOff(ActionEvent actionEvent) {
        setScene("editOffForSeller.fxml", "edit off");
    }

    public static Long getOffId() {
        return offId;
    }
}
