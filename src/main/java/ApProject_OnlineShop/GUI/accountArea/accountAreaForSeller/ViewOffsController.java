package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewOffsController extends FxmlController implements Initializable {
    private static int sortSlected;
    public VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (sortSlected == 0)
            for (String off : MainController.getInstance().getAccountAreaForSellerController().getAllOffs()) {
                Hyperlink hyperlink = new Hyperlink("- " + off);
                hyperlink.setOnMouseClicked(e -> viewSingleOff(off));
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                hyperlink.setFont(new Font(14));
                vBox.getChildren().add(hyperlink);
            }
        else {
            for (String off : MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(sortSlected)) {
                Hyperlink hyperlink = new Hyperlink("- " + off);
                hyperlink.setOnMouseClicked(e -> viewSingleOff(off));
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                hyperlink.setFont(new Font(14));
                vBox.getChildren().add(hyperlink);
            }
        }
    }

    private void viewSingleOff(String off) {
        long id = Long.valueOf(off.substring(8, off.indexOf("\t")));
        ViewSpecificOffController.setOffId(id);
        setScene("showSpecificOffForSeller.fxml", "Off");
    }

    public void backButton(ActionEvent actionEvent) {
        setScene("accountAreaForSeller.fxml", "account area");
    }

    public void logout(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
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
        setScene("createOffForSeller.fxml", "create off");
    }
}
