package ApProject_OnlineShop.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SuccessPageFxController extends FxmlController implements Initializable {
    @FXML
    private Label successTitleLabel;
    @FXML
    private Label successContentLabel;

    private static String successTitle;
    private static String successContent;
    private static Stage window;

    public void onOkButtonPressed() {
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        successTitleLabel.setText(successTitle);
        successContentLabel.setText(successContent);
    }

    public static void showPage(String successTitle, String successContent) {
        SuccessPageFxController.successTitle = successTitle;
        SuccessPageFxController.successContent = successContent;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image(SuccessPageFxController.class.getClassLoader().getResource("pictures/success1.png").toString()));
        window.setResizable(false);
        window.setTitle("success");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(SuccessPageFxController.class.getClassLoader().getResource("successPage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 400);
        window.setScene(scene);
        window.showAndWait();
    }
}
