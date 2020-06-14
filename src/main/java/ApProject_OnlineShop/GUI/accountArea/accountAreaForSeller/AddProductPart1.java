package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Optional;

public class AddProductPart1 extends FxmlController {
    @FXML
    public TextField name, brand, price, additionalDetails, availableNumber, subCategory;

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("manageProductsForSeller.fxml", "manage products");
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

    public void onAddProduct(ActionEvent actionEvent) {
        if (checkBaseInfos()) {
            ArrayList<String> productDetails = new ArrayList<>();
            productDetails.add(name.getText());
            productDetails.add(brand.getText());
            productDetails.add(price.getText());
            productDetails.add(availableNumber.getText());
            productDetails.add(additionalDetails.getText());
            productDetails.add(subCategory.getText());
            AddProductPart2.setProductDetails(productDetails);
            setScene("addProductForSellerPart2.fxml", "enter sub category properties");
        }
    }

    private boolean checkBaseInfos() {
        if (!name.getText().matches("[A-Za-z0-9\\s]+")) {
            ErrorPageFxController.showPage("Error for adding product", "name is invalid!");
            return false;
        } else if (!brand.getText().matches("\\w+")) {
            ErrorPageFxController.showPage("Error for adding product", "brand is invalid!");
            return false;
        } else if (!price.getText().matches("\\d\\d\\d\\d+")) {
            ErrorPageFxController.showPage("Error for adding product", "price is invalid!");
            return false;
        } else if (!availableNumber.getText().matches("[0-9]{1,5}")) {
            ErrorPageFxController.showPage("Error for adding product", "available number is invalid!,should be a number");
            return false;
        } else if (!MainController.getInstance().getAccountAreaForSellerController().isSubCategoryCorrect(subCategory.getText())) {
            ErrorPageFxController.showPage("Error for registering", "subcategory is invalid!");
            return false;
        }
        return true;
    }
}
