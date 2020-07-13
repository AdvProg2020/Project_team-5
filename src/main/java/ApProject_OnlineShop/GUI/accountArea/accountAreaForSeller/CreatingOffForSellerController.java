package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CreatingOffForSellerController extends FxmlController {
    @FXML
    public TextField maxAmount, discountPercentTextField;
    @FXML
    public DatePicker startDateChooser;
    @FXML
    public DatePicker endDateChooser;
    @FXML
    public TextField productToAdd;

    private ArrayList<Long> offProducts = new ArrayList<>();
    private boolean finished;

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("viewOffsForSeller.fxml", "Offs");
    }

    public void onCreateDiscountCodePressed(ActionEvent actionEvent) {
        if (checkInfos()) {
            if (!finished) {
                ErrorPageFxController.showPage("error for create off", "you should add products and press finished then create discount code");
            } else {
                ArrayList<String> offDetails = new ArrayList<>();
                offDetails.add(startDateChooser.getValue().toString());
                offDetails.add(endDateChooser.getValue().toString());
                offDetails.add(maxAmount.getText());
                offDetails.add(discountPercentTextField.getText());
                ArrayList<String> inputs = new ArrayList<>();
                inputs.addAll(offDetails);
                inputs.add("###");
                for (Long offProduct : offProducts) {
                    inputs.add(offProduct + "");
                }
                String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "addOff", getToken(), inputs));
                if (serverResponse.equals("off successfully added")) {
                    SuccessPageFxController.showPage("off request created successfully", "your request successfully sent to manager");
                    offProducts.clear();
                    finished = false;
                    setScene("accountAreaForSeller.fxml", "account area for seller");
                } else {
                    ErrorPageFxController.showPage("off request cannot be created", serverResponse);
                    finished = false;
                    offProducts.clear();
                }
//                try {
//                    MainController.getInstance().getAccountAreaForSellerController().addOff(offDetails, offProducts);
//                    SuccessPageFxController.showPage("off request created successfully", "your request successfully sent to manager");
//                    offProducts.clear();
//                    finished = false;
//                    setScene("accountAreaForSeller.fxml", "account area for seller");
//                } catch (Exception e) {
//                    ErrorPageFxController.showPage("off request cannot be created", e.getMessage());
//                    finished = false;
//                    offProducts.clear();
//                }
            }
        }
    }

    public void onFinishButtonPressed(ActionEvent actionEvent) {
        if (offProducts.size() == 0) {
            ErrorPageFxController.showPage("Error for adding products", "you should add at least one prodcut to this off");
        } else {
            finished = true;
        }
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
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

    public void onAddProductPressed(ActionEvent actionEvent) {
        if (!productToAdd.getText().matches("\\d+")) {
            ErrorPageFxController.showPage("error for adding product", "you should enter a product number");
            productToAdd.clear();
            return;
        }
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(productToAdd.getText());
        if (connectToServer(new RequestForServer("AccountAreaForSellerController", "checkValidProductId", getToken(), inputs)).equals("false")) {
            ErrorPageFxController.showPage("error for adding product", "This product does not exist your active goods");
            productToAdd.clear();
        } else {
            offProducts.add(Long.parseLong(productToAdd.getText()));
        }
    }

    private boolean checkInfos() {
        if (!maxAmount.getText().matches("\\d\\d\\d\\d+")) {
            ErrorPageFxController.showPage("error for create off", "max amount discount format not valid!");
            return false;
        } else if (!discountPercentTextField.getText().matches("[\\d]{1,2}")) {
            ErrorPageFxController.showPage("error for create off", "discount percent amount discount format not valid!");
            return false;
        } else if (!startDateChooser.getValue().isAfter(LocalDate.now())) {
            ErrorPageFxController.showPage("error for create off", "start date should be after now!");
            return false;
        } else if (!endDateChooser.getValue().isAfter(LocalDate.now())) {
            ErrorPageFxController.showPage("error for create off", "end date should be after now!");
            return false;
        } else if (!endDateChooser.getValue().isAfter(startDateChooser.getValue())) {
            ErrorPageFxController.showPage("error for create off", "end date should be after start date!");
            return false;
        } else {
            return true;
        }
    }
}
