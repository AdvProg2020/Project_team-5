package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountAreaForSellerController extends FxmlController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label balance;
    private static String pathBack;
    private static String titleBack;
    public ImageView photo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playMusicBackGround(false, false, true);
//        ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForSellerController().getUserPersonalInfo();
        ArrayList<String> personalInfo = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaController", "getUserPersonalInfo", getToken(), null)));
//        Image image = new Image(Paths.get("Resources/UserImages/" + FxmlController.getCurrentPerson().getUsername() + ".jpg").toUri().toString());

        Image image = new Image(new ByteArrayInputStream(connectToServerBytes(new RequestForServer("AccountAreaController", "getUserPhoto", getToken(), null))));
//        File file = new File("Resources\\UserImages\\" + FxmlController.getCurrentPerson().getUsername() + ".jpg");
//        if (!file.exists())
//            image = new Image(Paths.get("Resources/UserImages/default1.jpg").toUri().toString());
        photo.setImage(image);
        userName.setText(personalInfo.get(0));
        name.setText(personalInfo.get(1));
        lastName.setText(personalInfo.get(2));
        email.setText(personalInfo.get(3));
        phoneNumber.setText(personalInfo.get(4));
        balance.setText(connectToServer(new RequestForServer("AccountAreaForSellerController", "viewBalance", getToken(), null)));
    }

    public void viewCompanyInformation(MouseEvent mouseEvent) {
        setScene("showCompanyInformation.fxml", "Company Information");
    }

    public void viewSaleHistory(MouseEvent mouseEvent) {
        (new ViewOrdersForSeller()).viewSortedOrders(0);
    }

    public void manageProducts(MouseEvent mouseEvent) {
        setScene("manageProductsForSeller.fxml", "manage products");
    }

    public void showCategories(MouseEvent mouseEvent) {
        setScene("showCategoriesForSeller.fxml", "categories");
    }

    public void viewOffs(MouseEvent mouseEvent) {
        setScene("viewOffsForSeller.fxml", "Offs");
    }

    public void onCreateAuctionClicked() {
        setScene("creatingAuctionPage.fxml", "Create Auction");
    }

    public void onViewAuctionsPressed() {
        setScene("viewAuctionsPage.fxml", "View Auction");
    }

    public void onAddFileProductClicked() {
        setScene("addFileProductForSeller.fxml", "add file product");
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

    public void backButton() {
        setScene(pathBack, titleBack);
    }

    public void editField() {
        setScene("editFieldPersons.fxml", "edit field");
    }

    public static void setPathBack(String pathBack, String titleBack) {
        AccountAreaForSellerController.pathBack = pathBack;
        AccountAreaForSellerController.titleBack = titleBack;
    }

    public void withdrawMoney() {
        setScene("withdrawMoney.fxml", "withdraw money");
    }

    public void depositMoney(ActionEvent actionEvent) {
        setScene("depositMoney.fxml", "deposit money");
    }
}
