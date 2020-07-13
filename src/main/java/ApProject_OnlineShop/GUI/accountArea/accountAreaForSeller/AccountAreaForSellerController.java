package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
        Image image = new Image(Paths.get("Resources/UserImages/" + FxmlController.getCurrentPerson().getUsername() + ".jpg").toUri().toString());
        File file = new File("Resources\\UserImages\\" + FxmlController.getCurrentPerson().getUsername() + ".jpg");
        if (!file.exists())
            image = new Image(Paths.get("Resources/UserImages/default1.jpg").toUri().toString());
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

    public void logout(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Shop.getInstance().clearCart();
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
