package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSubCategoryPageController extends FxmlController implements Initializable {
    private static String currentCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void setCurrentCategory(String currentCategory) {
        AddSubCategoryPageController.currentCategory = currentCategory;
    }
}
