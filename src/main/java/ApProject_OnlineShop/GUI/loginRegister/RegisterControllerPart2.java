package ApProject_OnlineShop.GUI.loginRegister;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.userExceptions.MainManagerAlreadyRegistered;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import ApProject_OnlineShop.model.productThings.Good;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class RegisterControllerPart2 extends FxmlController {
    private static ArrayList<String> details;
    private static String role;
    private static String userName;
    private String path;
    private File selectedFile;

    public void backButtonAction(ActionEvent actionEvent) {
        if (role.equals("seller"))
            setScene("registerSeller.fxml", "register seller");
        else if (role.equals("manager"))
            setScene("registerManager.fxml", "register manager");
        else if (role.equals("customer"))
            setScene("registerCustomer.fxml", "register customer");
    }

    public void registerButtonPressed(ActionEvent actionEvent) {
        try {
            MainController.getInstance().getLoginRegisterController().createAccount
                    (role, userName, details);
            SuccessPageFxController.showPage("Register was successful",
                    "you registered successfully");
            if (selectedFile != null)
                copyPhoto();
            setScene("login.fxml", "Login");
        } catch (UsernameIsTakenAlreadyException | FileCantBeSavedException | MainManagerAlreadyRegistered | IOException e) {
            ErrorPageFxController.showPage("Error for registering", e.getMessage());
            if (role.equals("seller"))
                setScene("registerSeller.fxml", "register seller");
            else if (role.equals("manager"))
                setScene("registerManager.fxml", "register manager");
            else if (role.equals("customer"))
                setScene("registerCustomer.fxml", "register customer");
        }
    }

    private void copyPhoto() {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(selectedFile.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ImageIO.write(bi, "jpg", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSelectPhoto(ActionEvent actionEvent) {
        File file = new File("Resources\\UserImages");
        if (!file.exists())
            file.mkdir();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("png", "*.png");
        FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("jpg", "*.jpg");
        fileChooser.getExtensionFilters().addAll(png,jpg);
        selectedFile = fileChooser.showOpenDialog(StageController.getStage());
        path = "./Resources/UserImages/" + userName + ".jpg";
    }

    public static void setDetails2(ArrayList<String> details) {
        RegisterControllerPart2.details = details;
    }

    public static void setRole(String role) {
        RegisterControllerPart2.role = role;
    }

    public static void setUserName(String userName) {
        RegisterControllerPart2.userName = userName;
    }
}
