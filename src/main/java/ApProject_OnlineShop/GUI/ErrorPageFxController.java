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

public class ErrorPageFxController extends FxmlController implements Initializable {
    @FXML
    private Label errorTitleLabel;
    @FXML
    private Label errorContentLabel;

    private static String errorTitle;
    private static String errorContent;
    private static Stage window;

    public void onOkButtonPressed() {
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorTitleLabel.setText(errorTitle);
        errorContentLabel.setText(errorContent);
    }

    public static void showPage(String errorTitle, String errorContent) {
        ErrorPageFxController.errorTitle = errorTitle;
        ErrorPageFxController.errorContent = errorContent;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image(ErrorPageFxController.class.getClassLoader().getResource("pictures/error1.png").toString()));
        window.setResizable(false);
        window.setTitle("error");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(ErrorPageFxController.class.getClassLoader().getResource("errorPage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 400);
        window.setScene(scene);
        window.showAndWait();
    }
}
