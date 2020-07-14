package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionPageController extends FxmlController implements Initializable {

    private static String selectedAuctionId;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void setSelectedAuctionId(String selectedAuctionId) {
        AuctionPageController.selectedAuctionId = selectedAuctionId;
    }
}
