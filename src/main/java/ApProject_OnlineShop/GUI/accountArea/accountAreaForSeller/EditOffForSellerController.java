package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Off;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditOffForSellerController extends FxmlController implements Initializable {
    @FXML
    public TextField maxAmount, discountPercentTextField, removeProduct, addProduct;
    @FXML
    public DatePicker startDateChooser;
    @FXML
    public DatePicker endDateChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + ViewSpecificOffController.getOffId());
        ArrayList<String> offDetails = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "viewOffGUI", getToken(), inputs)));
//        ArrayList<String> offDetails = MainController.getInstance().getAccountAreaForSellerController().viewOffGUI(ViewSpecificOffController.getOffId());
        maxAmount.setPromptText(offDetails.get(3));
        discountPercentTextField.setPromptText(offDetails.get(4));
        startDateChooser.setValue(LocalDate.parse(offDetails.get(1)));
        endDateChooser.setValue(LocalDate.parse(offDetails.get(2)));
    }

    public void onEditOff(ActionEvent actionEvent) {
        boolean edited = false;
        Long id = ViewSpecificOffController.getOffId();
        if (checkBaseInfos()) {
            if (!maxAmount.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForSellerController()
                            .editOff("max discount", maxAmount.getText(), id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!discountPercentTextField.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForSellerController()
                            .editOff("discount percent", discountPercentTextField.getText(), id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!startDateChooser.getValue().equals(Shop.getInstance().findOffById(id).getStartDate())) {
                try {
                    MainController.getInstance().getAccountAreaForSellerController()
                            .editOff("start date", startDateChooser.getValue().toString(), id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!endDateChooser.getValue().equals(Shop.getInstance().findOffById(id).getEndDate())) {
                try {
                    MainController.getInstance().getAccountAreaForSellerController()
                            .editOff("end date", endDateChooser.getValue().toString(), id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!addProduct.getText().equals("")) {
                try {
                    long productId = Long.parseLong(addProduct.getText());
                    MainController.getInstance().getAccountAreaForSellerController().editOff("add good", "" + productId, id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (!removeProduct.getText().equals("")) {
                try {
                    long productId = Long.parseLong(removeProduct.getText());
                    MainController.getInstance().getAccountAreaForSellerController().editOff("remove good", "" + productId, id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field", exception.getMessage());
                    edited = false;
                }
            }
            if (edited) {
                SuccessPageFxController.showPage("edit request sent", "edit request sent to manager succesfully!");
            }
        }
    }

    private boolean checkBaseInfos() {
        if (!maxAmount.getText().matches("\\d\\d\\d\\d+") && !maxAmount.getText().equals("")) {
            ErrorPageFxController.showPage("error for create off", "max amount discount format not valid!");
            return false;
        } else if (!discountPercentTextField.getText().matches("[\\d]{1,2}") && !discountPercentTextField.getText().equals("")) {
            ErrorPageFxController.showPage("error for create off", "discount percent amount discount format not valid!");
            return false;
        } else if (!endDateChooser.getValue().isAfter(LocalDate.now())) {
            ErrorPageFxController.showPage("error for create off", "end date should be after now!");
            return false;
        } else if (!endDateChooser.getValue().isAfter(startDateChooser.getValue())) {
            ErrorPageFxController.showPage("error for create off", "end date should be after start date!");
            return false;
        } else if (removeProduct.getText().matches("[\\d]+")) {
            long productId = Long.parseLong(removeProduct.getText());
            if (!Shop.getInstance().findOffById(ViewSpecificOffController.getOffId()).doesHaveThisProduct(Shop.getInstance().findGoodById(productId))) {
                ErrorPageFxController.showPage("error for edit off", "you don't have this product in your off");
                return false;
            }
        } else if (addProduct.getText().matches("[\\d]+")) {
            long productId = Long.parseLong(addProduct.getText());
            if (Shop.getInstance().findGoodById(productId) == null) {
                ErrorPageFxController.showPage("error for edit off", "doesn't exist a product with this id");
                return false;
            } else if (!MainController.getInstance().getAccountAreaForSellerController().checkValidProductId(productId)) {
                ErrorPageFxController.showPage("error for edit off", "This product does not exist your active goods");
                return false;
            } else if (Shop.getInstance().findOffById(ViewSpecificOffController.getOffId()).doesHaveThisProduct(Shop.getInstance().findGoodById(productId))) {
                ErrorPageFxController.showPage("error for edit off", "you have this product in your off already");
                return false;
            }
        }
        return true;
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("showSpecificOffForSeller.fxml", "view off");
    }
}
