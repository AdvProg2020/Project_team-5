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

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditPorductController extends FxmlController implements Initializable {
    @FXML
    public GridPane gridpane;
    @FXML
    public TextField price, additionalDetails, availableNumber;
    private static long goodId ;
    private HashMap<String, TextField> textFields = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Good good = Shop.getInstance().findGoodById(goodId);
        price.setPromptText(good.getPriceBySeller((Seller) MainController.getInstance().getCurrentPerson()) + "");
        additionalDetails.setPromptText(good.getDetails());
        availableNumber.setPromptText(good.getAvailableNumberBySeller((Seller) MainController.getInstance().getCurrentPerson()) + "");
        int row = 3;
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
        EditPorductController.goodId = goodId;
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
        long id = goodId;
        boolean edited = false;
        if (checkBaseInfos()) {
            if (!price.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForSellerController()
                            .editProduct("price", price.getText(), id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field price", exception.getMessage());
                    edited = false;
                }
            }
            if (!availableNumber.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForSellerController()
                            .editOff("availableNumber", availableNumber.getText(), id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field available number", exception.getMessage());
                    edited = false;
                }
            }
            if (!additionalDetails.getText().equals("")) {
                try {
                    MainController.getInstance().getAccountAreaForSellerController().editOff("details", additionalDetails.getText(), id);
                    edited = true;
                } catch (Exception exception) {
                    ErrorPageFxController.showPage("can not edited field details", exception.getMessage());
                    edited = false;
                }
            }
            for (String detail : textFields.keySet()) {
                if (!textFields.get(detail).getText().equals("")) {
                    try {
                        MainController.getInstance().getAccountAreaForSellerController().editOff(detail, textFields.get(detail).getText(), id);
                        edited = true;
                    } catch (Exception exception) {
                        ErrorPageFxController.showPage("can not edited field" + detail, exception.getMessage());
                        edited = false;
                    }
                }
            }
            if (edited) {
                SuccessPageFxController.showPage("edit good request sent", "edit good request sent to manager succesfully!");
            }
        }
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

