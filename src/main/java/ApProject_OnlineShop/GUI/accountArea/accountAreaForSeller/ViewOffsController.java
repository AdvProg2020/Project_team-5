package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.RequestForServer;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewOffsController extends FxmlController implements Initializable {
    private static int sortSlected;
    public VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> offs = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getAllOffs", getToken(), null)));
        if (sortSlected == 0)
            for (String off : offs) {
                Hyperlink hyperlink = new Hyperlink("- " + off);
                hyperlink.setPrefSize(200, 50);
                hyperlink.setOnMouseClicked(e -> viewSingleOff(off));
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                hyperlink.setFont(new Font(14));
                vBox.getChildren().add(hyperlink);
            }
        else {
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(sortSlected + "");
            List<String> sortedOffs = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getSortedOffs", getToken(), inputs)));
            for (String off : sortedOffs) {
                Hyperlink hyperlink = new Hyperlink("- " + off);
                hyperlink.setOnMouseClicked(e -> viewSingleOff(off));
                hyperlink.setPrefSize(200, 50);
                hyperlink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
                hyperlink.setAlignment(Pos.BOTTOM_LEFT);
                hyperlink.setPadding(new Insets(8));
                hyperlink.setUnderline(false);
                hyperlink.setFont(new Font(14));
                vBox.getChildren().add(hyperlink);
            }
        }
        int size = offs.size() * 50;
        if (size > 348) {
            vBox.setPrefHeight(size);
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
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), getInputsForServer()));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
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
