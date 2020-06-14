package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class editPorductController extends FxmlController implements Initializable {
    @FXML
    public GridPane gridpane;
    private static long goodId;
    private File selectedFile;
    private String path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HashMap<String, String> detailValues = new HashMap<>();
        int row = 4;
        for (String detail : MainController.getInstance().getAccountAreaForSellerController().getSubcategoryDetails(Shop.getInstance().findGoodById(goodId).getSubCategory().getName())) {
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
            row++;
        }
    }

    public static void setGoodId(long goodId) {
        editPorductController.goodId = goodId;
    }
}

