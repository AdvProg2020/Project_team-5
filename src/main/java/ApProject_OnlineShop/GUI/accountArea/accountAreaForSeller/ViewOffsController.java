package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewOffsController extends FxmlController implements Initializable {
    private static int sortSlected;
    public VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (sortSlected == 0)
            for (String off : MainController.getInstance().getAccountAreaForSellerController().getAllOffs()) {
                Hyperlink hyperlink = new Hyperlink(off);
                hyperlink.setOnMouseClicked(e -> viewSingleOff(off));
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                vBox.getChildren().add(hyperlink);
            }
        else {
            for (String off : MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(sortSlected)) {
                Hyperlink hyperlink = new Hyperlink(off);
                hyperlink.setOnMouseClicked(e -> viewSingleOff(off));
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                vBox.getChildren().add(hyperlink);
            }
        }
    }

    private void viewSingleOff(String off) {
    }

    public void backButton(ActionEvent actionEvent) {
    }

    public void logout(MouseEvent mouseEvent) {
    }

    public void sortByEndDate(ActionEvent actionEvent) {
        sortSlected = 1;
        setScene("viewOffsForSeller.fxml", "offs");
    }

    public void sortByOffPercent(ActionEvent actionEvent) {
        sortSlected = 2;
        setScene("viewOffsForSeller.fxml", "offs");
    }

    public void sortByMaxDiscount(ActionEvent actionEvent) {
        sortSlected = 3;
        setScene("viewOffsForSeller.fxml", "offs");
    }

    public void addOff(MouseEvent mouseEvent) {
    }
}
