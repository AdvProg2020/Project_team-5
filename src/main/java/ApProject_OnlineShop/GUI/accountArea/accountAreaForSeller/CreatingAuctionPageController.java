package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreatingAuctionPageController extends FxmlController implements Initializable {
    @FXML
    private TextField titleTextField;

    @FXML
    private TextField productTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private DatePicker startDateChooser;

    @FXML
    private DatePicker endDateChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void clearFields() {
        productTextField.clear();
        titleTextField.clear();
        descriptionTextArea.clear();
    }

    public void onCreateAuctionPressed() {
        if (checkInformationValidation()) {
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(productTextField.getText());
            inputs.add(titleTextField.getText());
            inputs.add((descriptionTextArea.getText().isEmpty())?"":descriptionTextArea.getText());
            inputs.add(startDateChooser.getValue().toString());
            inputs.add(endDateChooser.getValue().toString());
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "createAuction", getToken(), inputs));
            if (serverResponse.equals("auction successfully created")) {
                SuccessPageFxController.showPage("auction created successfully", "your auction successfully created.");
                clearFields();
                setScene("accountAreaForSeller.fxml", "account area for seller");
            } else {
                ErrorPageFxController.showPage("auction cannot be created", serverResponse);
                clearFields();
            }
        }
    }

    private boolean checkInformationValidation() {
        if (productTextField.getText().isEmpty() || titleTextField.getText().isEmpty()) {
            ErrorPageFxController.showPage("error for create auction", "please fill all fields and then click create.");
            clearFields();
            return false;
        } else if (!startDateChooser.getValue().isAfter(LocalDate.now())) {
            ErrorPageFxController.showPage("error for create auction", "start date should be after now!");
            clearFields();
            return false;
        } else if (!endDateChooser.getValue().isAfter(LocalDate.now())) {
            ErrorPageFxController.showPage("error for create auction", "end date should be after now!");
            clearFields();
            return false;
        } else if (!endDateChooser.getValue().isAfter(startDateChooser.getValue())) {
            ErrorPageFxController.showPage("error for create auction", "end date should be after start date!");
            clearFields();
            return false;
        } else if (!productTextField.getText().matches("\\d+")) {
            ErrorPageFxController.showPage("error for create auction", "product id should be a numeric string.");
            clearFields();
            return false;
        } else {
            return true;
        }
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            //MainController.getInstance().getLoginRegisterController().logoutUser();
            //Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), null));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }


    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("accountAreaForSeller.fxml", "account area");
    }
}
