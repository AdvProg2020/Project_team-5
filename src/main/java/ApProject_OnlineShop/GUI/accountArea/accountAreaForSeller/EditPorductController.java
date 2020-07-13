package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.server.RequestForServer;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditPorductController extends FxmlController implements Initializable {
    @FXML
    public GridPane gridpane;
    @FXML
    public TextField price, additionalDetails, availableNumber;
    private static long goodId;
    private HashMap<String, TextField> textFields = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> inputs2 = new ArrayList<>();
        inputs2.add(goodId + "");
        Good good = new Gson().fromJson(connectToServer(new RequestForServer("Shop", "findGoodById", null, inputs2)), Good.class);
        price.setPromptText(good.getPriceBySeller((Seller) getCurrentPerson()) + "");
        additionalDetails.setPromptText(good.getDetails());
        availableNumber.setPromptText(good.getAvailableNumberBySeller((Seller) getCurrentPerson()) + "");
        int row = 3;
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(good.getSubCategory().getName());
        ArrayList<String> subCategoryDetails = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getSubcategoryDetails", getToken(), inputs)));
        for (String detail : subCategoryDetails) {
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
        setScene("productPageEditableForSeller.fxml", "product page");
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

    public void onEditProduct(ActionEvent actionEvent) {
        long id = goodId;
        boolean edited = false;
        if (checkBaseInfos()) {
            if (!price.getText().equals("")) {
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("price");
                inputs.add(price.getText());
                inputs.add("" + id);
                String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "editProduct", getToken(), inputs));
                if (serverResponse.equals("product edited successfully")) {
                    edited = true;
                } else {
                    ErrorPageFxController.showPage("can not edited field", serverResponse);
                    edited = false;
                }
//                try {
//                    MainController.getInstance().getAccountAreaForSellerController()
//                            .editProduct("price", price.getText(), id);
//                    edited = true;
//                } catch (Exception exception) {
//                    ErrorPageFxController.showPage("can not edited field price", exception.getMessage());
//                    edited = false;
//                }
            }
            if (!availableNumber.getText().equals("")) {
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("availableNumber");
                inputs.add(availableNumber.getText());
                inputs.add("" + id);
                String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "editProduct", getToken(), inputs));
                if (serverResponse.equals("product edited successfully")) {
                    edited = true;
                } else {
                    ErrorPageFxController.showPage("can not edited field", serverResponse);
                    edited = false;
                }
//                try {
//                    MainController.getInstance().getAccountAreaForSellerController()
//                            .editProduct("availableNumber", availableNumber.getText(), id);
//                    edited = true;
//                } catch (Exception exception) {
//                    ErrorPageFxController.showPage("can not edited field available number", exception.getMessage());
//                    edited = false;
//                }
            }
            if (!additionalDetails.getText().equals("")) {
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("details");
                inputs.add(additionalDetails.getText());
                inputs.add("" + id);
                String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "editProduct", getToken(), inputs));
                if (serverResponse.equals("product edited successfully")) {
                    edited = true;
                } else {
                    ErrorPageFxController.showPage("can not edited field", serverResponse);
                    edited = false;
                }
//                try {
//                    MainController.getInstance().getAccountAreaForSellerController().editProduct("details", additionalDetails.getText(), id);
//                    edited = true;
//                } catch (Exception exception) {
//                    ErrorPageFxController.showPage("can not edited field details", exception.getMessage());
//                    edited = false;
//                }
            }
            for (String detail : textFields.keySet()) {
                if (!textFields.get(detail).getText().equals("")) {
                    ArrayList<String> inputs = new ArrayList<>();
                    inputs.add(detail);
                    inputs.add(textFields.get(detail).getText());
                    inputs.add("" + id);
                    String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "editProduct", getToken(), inputs));
                    if (serverResponse.equals("product edited successfully")) {
                        edited = true;
                    } else {
                        ErrorPageFxController.showPage("can not edited field", serverResponse);
                        edited = false;
                    }
//                    try {
//                        MainController.getInstance().getAccountAreaForSellerController().editProduct(detail, textFields.get(detail).getText(), id);
//                        edited = true;
//                    } catch (Exception exception) {
//                        ErrorPageFxController.showPage("can not edited field" + detail, exception.getMessage());
//                        edited = false;
//                    }
                }
            }
            if (edited) {
                SuccessPageFxController.showPage("edit good request sent", "edit good request sent to manager succesfully!");
                setScene("productPageEditableForSeller.fxml", "product page");
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

