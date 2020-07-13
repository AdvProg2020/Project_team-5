package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountAreaForSupporter extends FxmlController implements Initializable {
    public Label userName;
    public Label name;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public ImageView photo;
    private static String pathBack;
    private static String titleBack;

    public void onBackButtonPressed() {
        setScene("mainMenuLayout.fxml", "main menu");
    }

    public void onLogoutClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playMusicBackGround(false, false, true);
        ArrayList<String> personalInfo = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaController", "getUserPersonalInfo", getToken(), null)));
        Image image = new Image(Paths.get("Resources/UserImages/" + getCurrentPerson().getUsername() + ".jpg").toUri().toString());
        File file = new File("Resources\\UserImages\\" + getCurrentPerson().getUsername() + ".jpg");
        if (!file.exists())
            image = new Image(Paths.get("Resources/UserImages/default1.jpg").toUri().toString());
        photo.setImage(image);
        userName.setText(personalInfo.get(0));
        name.setText(personalInfo.get(1));
        lastName.setText(personalInfo.get(2));
        email.setText(personalInfo.get(3));
        phoneNumber.setText(personalInfo.get(4));
    }

    public static void setPathBack(String pathBack, String titleBack) {
        AccountAreaForSupporter.pathBack = pathBack;
        AccountAreaForSupporter.titleBack = titleBack;
    }

    public void chaWithCustomers(ActionEvent actionEvent) {

    }
}
