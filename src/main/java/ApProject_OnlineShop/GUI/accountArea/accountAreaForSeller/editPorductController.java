package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class editPorductController extends FxmlController implements Initializable {
    @FXML
    public GridPane gridpane;
    @FXML
    public TextField price, additionalDetails, availableNumber;
    private static long goodId = 7;
    private File selectedFile;
    private String path;
    private HashMap<String, TextField> textFields = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Good good = Shop.getInstance().findGoodById(goodId);
        price.setPromptText(good.getPriceBySeller((Seller) MainController.getInstance().getCurrentPerson()) + "");
        additionalDetails.setPromptText(good.getDetails());
        availableNumber.setPromptText(good.getAvailableNumberBySeller((Seller) MainController.getInstance().getCurrentPerson()) + "");
        int row = 4;
        for (String detail : MainController.getInstance().getAccountAreaForSellerController().getSubcategoryDetails(good.getSubCategory().getName())) {
            Label text = new Label(detail + " :");
            text.setFont(Font.font("Times New Roman", 14));
            text.setPadding(new Insets(20));
            GridPane.setHalignment(text, HPos.LEFT);
            gridpane.add(text, 0, row);
            TextField textField = new TextField();
            textField.setPromptText(good.getCategoryProperties().get(detail));
            textField.setAlignment(Pos.CENTER);
            textField.setPrefSize(167, 28);
            textField.setMaxSize(167, 28);
            GridPane.setHalignment(textField, HPos.CENTER);
            gridpane.add(textField, 1, row);
            textFields.put(detail, textField);
            row++;
        }
    }

    public static void setGoodId(long goodId) {
        editPorductController.goodId = goodId;
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("", "product page");
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

    public void onEditProduct(ActionEvent actionEvent) {
//        if (checkBaseInfos()) {
//            HashMap<String, String> detailValues = new HashMap<>();
//            for (String details : textFields.keySet()) {
//                detailValues.put(details, textFields.get(details).getText());
//                if (textFields.get(details).getText().equals("")) {
//                    ErrorPageFxController.showPage("can not add good", "fields should be filled!");
//                    return;
//                }
//            }
//            if (selectedFile == null) {
//                ErrorPageFxController.showPage("can not add good", "you should chose a photo");
//                return;
//            }
//            try {
//                MainController.getInstance().getAccountAreaForSellerController().addProduct(productDetails, detailValues);
//                SuccessPageFxController.showPage("adding good was successful", "adding good request successfully sent to manager!");
//                setScene("manageProductsForSeller.fxml", "manage product");
//            } catch (Exception e) {
//                ErrorPageFxController.showPage("can not add good", e.getMessage());
//            }
//        }
    }

    private boolean checkBaseInfos() {
        if (!price.getText().matches("\\d\\d\\d\\d+") && !price.getText().equals("")) {
            ErrorPageFxController.showPage("Error for editing product", "price is invalid!");
            return false;
        } else if (!availableNumber.getText().matches("[0-9]{1,5}") && !availableNumber.getText().equals("")) {
            ErrorPageFxController.showPage("Error for editing product", "available number is invalid!,should be a number");
            return false;
        }
        return true;
    }
}

