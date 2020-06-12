package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductPart2 extends FxmlController implements Initializable {
    @FXML
    public GridPane gridpane;

    private static ArrayList<String> productDetails;
    private File selectedFile;
    private String path;
    private HashMap<String, TextField> textFields = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HashMap<String, String> detailValues = new HashMap<>();
        int row = 1;
        for (String detail : MainController.getInstance().getAccountAreaForSellerController().getSubcategoryDetails(productDetails.get(5))) {
            Label text = new Label(detail + " :");
            text.setFont(Font.font("Times New Roman", 16));
            text.setPadding(new Insets(20));
            GridPane.setHalignment(text, HPos.LEFT);
            gridpane.add(text, 0, row);
            TextField textField = new TextField();
            textField.setPromptText(detail);
            textField.setAlignment(Pos.CENTER);
            textField.setPrefSize(167, 28);
            textField.setMaxSize(167, 28);
            GridPane.setHalignment(textField, HPos.CENTER);
            gridpane.add(textField, 1, row);
            textFields.put(detail, textField);
            row++;
        }
    }

    public void onAddProduct(ActionEvent actionEvent) {
        HashMap<String, String> detailValues = new HashMap<>();
        for (String details : textFields.keySet()) {
            detailValues.put(details, textFields.get(details).getText());
            if (textFields.get(details).getText().equals("")) {
                ErrorPageFxController.showPage("can not add good", "fields should be filled!");
                return;
            }
        }
        if(selectedFile == null){
            ErrorPageFxController.showPage("can not add good", "you should chose a photo");
            return;
        }
        try {
            MainController.getInstance().getAccountAreaForSellerController().addProduct(productDetails, detailValues);
            SuccessPageFxController.showPage("adding good was successful", "adding good request successfully sent to manager!");
        } catch (Exception e) {
            ErrorPageFxController.showPage("can not add good", e.getMessage());
        }
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("addProductForSeller.fxml", "add product");
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

    public static ArrayList<String> getProductDetails() {
        return productDetails;
    }

    public static void setProductDetails(ArrayList<String> productDetails) {
        AddProductPart2.productDetails = productDetails;
    }

    public void selectPhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
         selectedFile = fileChooser.showOpenDialog(StageController.getStage());
        try {
//            java.nio.file.Files.copy(selectedFile.toPath(), Paths.get("src/"));
            path = "./resources/productImages/" + Good.getGoodsCount() + ".png";
            Files.copy(selectedFile.toPath(),
                    (new File(path)).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
