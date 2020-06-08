package ApProject_OnlineShop.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PurchasePageController implements Initializable {
    @FXML
    private Label shouldPayLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button purchaseButton;
    @FXML
    private Button applyDiscountButton;
    @FXML
    private Button submitPropertiesButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField discountCodeField;

    public void onBackButtonPressed() {

    }

    public void onSubmitButtonPressed() {
        String name = nameField.getText();
        String postalCode = postalCodeField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty()) {
            //TODO
        }
        /*
        write logic
         */
        applyDiscountButton.setDisable(false);
        purchaseButton.setDisable(false);
    }

    public void onApplyDiscountButtonPressed() {

    }

    public void onPurchaseButtonPressed() {
        //TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyDiscountButton.setDisable(true);
        purchaseButton.setDisable(true);
    }
}
