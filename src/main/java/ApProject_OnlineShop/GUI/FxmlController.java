package ApProject_OnlineShop.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class FxmlController {

    public void setScene(String address, String title) {
        playButtonMusic();
        Stage stage = StageController.getStage();
        Parent rootParent = null;
        try {
            rootParent = FXMLLoader.load(getClass().getClassLoader().getResource(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(title);
        stage.setScene(new Scene(rootParent, 1000, 800));
        // stage.setMaximized(true);
        stage.show();
    }

    public Optional<ButtonType> showAlert(Alert.AlertType alertType, String title, String header, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    public static void playButtonMusic() {
        String resource2 = Paths.get("src/main/resources/musics/buttonsEffects/Keyboard_Button_1-fesliyanstudios.com.mp3").toUri().toString();
        realPlay(resource2);
    }

    public static void playErrorMusic() {
        String resource2 = Paths.get("src/main/resources/musics/buttonsEffects/Error-sound.mp3").toUri().toString();
        realPlay(resource2);
    }

    private static void realPlay(String url) {
        AudioClip audioClip = new AudioClip(url);
        audioClip.setVolume(0.2);
        audioClip.play();
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("effect played");
    }

}
