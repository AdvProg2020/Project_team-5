package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.model.productThings.Good;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductPart2 extends FxmlController implements Initializable {

    private static ArrayList<String> productDetails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onAddProduct(ActionEvent actionEvent) {
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
    }

    public static ArrayList<String> getProductDetails() {
        return productDetails;
    }

    public static void setProductDetails(ArrayList<String> productDetails) {
        AddProductPart2.productDetails = productDetails;
    }

    public void selectPhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(StageController.getStage());
        try {
            java.nio.file.Files.copy(selectedFile.toPath(), Paths.get("src/"));
            Files.copy(selectedFile.toPath(),
                    (new File("./resources/productImages/" + Good.getGoodsCount() + ".png")).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
