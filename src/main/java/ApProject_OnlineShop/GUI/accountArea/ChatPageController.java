package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ChatPageController extends FxmlController implements Initializable {
    private static String owner;
    private static String guest;
    private static String path;
    private static String backTitle;
    public TextField massageTextField;
    public VBox vBox;
    public Label title;
    public ScrollPane scrollPane;
    private static double vValue;
    public ArrayList<Massage> massages;
    Timer t;
    static Timer previousTimer;
    int numberOfChats;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        if (previousTimer != null) {
//            previousTimer.cancel();
//            System.out.println("previous canceled");
//        }
        title.setText("chat with " + guest);
        loadChats();
        new Thread(new ChatPageThread(this, owner, guest, scrollPane)).start();
//        t = new Timer();
//        previousTimer = t;
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                vValue = scrollPane.getVvalue();
//                System.out.println("hi");
////                setScene("chatPage.fxml", "chat page");
//                Parent rootParent = null;
//                try {
//                    rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("chatPage.fxml"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                StageController.getStage().setScene(new Scene(rootParent, 1000, 800));
//            }
//        }, 200, 3000);
//        new Thread(new Runnable() {
//            public void run() {
////                long pastTime = System.currentTimeMillis();
////                while (true) {
////                    long test = System.currentTimeMillis();
////                    if (test >= (pastTime + 5 * 1000)) { //multiply by 1000 to get milliseconds
////                        ArrayList<String> inputs = new ArrayList<>();
////                        inputs.add(owner);
////                        inputs.add(guest);
////                        ArrayList<Massage> massages = new Gson().fromJson(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getMassages", getToken(), inputs)), new TypeToken<ArrayList<Massage>>() {
////                        }.getType());
////                        if (massages.size() > numberOfChats) {
////                            pastTime = test;
////                            vValue = scrollPane.getVvalue();
////                            System.out.println("hi");
////                            Parent rootParent = null;
////                            try {
////                                rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("chatPage.fxml"));
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                            }
////                            StageController.getStage().setScene(new Scene(rootParent, 1000, 800));
////                        }
////                    }
////                }
//            }
//        }).start();
    }

    synchronized public void loadChats() {
        vBox.getChildren().clear();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(owner);
        inputs.add(guest);
        ArrayList<Massage> massages = new Gson().fromJson(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getMassages", getToken(), inputs)), new TypeToken<ArrayList<Massage>>() {
        }.getType());
        for (Massage massage : massages) {
            HBox hBox = new HBox();
            if (massage.getSenderUserName().equals(owner))
                hBox.setAlignment(Pos.CENTER_RIGHT);
            else
                hBox.setAlignment(Pos.CENTER_LEFT);
            VBox massageBox = getMassageVBox(massage);
            VBox.setMargin(hBox, new Insets(5, 15, 5, 10));
            hBox.getChildren().add(massageBox);
            vBox.getChildren().add(hBox);
        }
        vBox.getChildren().add(getAnswerBox());
        scrollPane.setVvalue(vValue);
    }

    public VBox getMassageVBox(Massage massage) {
        VBox vBox = new VBox();
        vBox.setMaxWidth(240);
        vBox.setMinWidth(240);
        vBox.setStyle("-fx-border-color:#8600b3; -fx-border-width: 1;-fx-border-style: solid; -fx-border-radius: 10px;");
        vBox.setAlignment(Pos.CENTER_LEFT);
        Label senderName = new Label(massage.getSenderUserName());
        VBox.setMargin(senderName, new Insets(6, 0, 0, 10));
        senderName.setFont(Font.font("Times New Roman", 15));
        senderName.setTextFill(Color.BLUE);
        vBox.getChildren().add(senderName);
        Label body = new Label(massage.getMassage());
        VBox.setMargin(body, new Insets(6, 0, 0, 10));
        body.setFont(Font.font("Times New Roman", 13));
        vBox.getChildren().add(body);
        String time = "" + massage.getTime();
        Label timeText = new Label(time.substring(0, 2) + ":" + time.substring(3, 5));
        HBox timeHBox = new HBox();
        timeText.setFont(Font.font("Times New Roman", 13));
        timeText.setPadding(new Insets(7, 10, 5, 15));
        timeHBox.setAlignment(Pos.CENTER_RIGHT);
        timeHBox.getChildren().add(timeText);
        vBox.getChildren().add(timeHBox);
        return vBox;
    }

    public HBox getAnswerBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(40);
        hBox.setPrefWidth(442);
        this.massageTextField = new TextField();
        this.massageTextField.setPromptText("type your massage");
        this.massageTextField.setFont(Font.font(14));
        this.massageTextField.setPrefWidth(388);
        this.massageTextField.setMinHeight(30);
        this.massageTextField.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(massageTextField);
        HBox.setMargin(massageTextField, new Insets(7, 0, 1, 7));
        ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/sendIcon.png").toString()));
        imageView.setCursor(Cursor.HAND);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked(e -> sendMassage());
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        HBox.setMargin(imageView, new Insets(0, 3, 0, 7));
        hBox.getChildren().add(imageView);
        return hBox;
    }

    private void sendMassage() {
        Massage massage = new Massage(owner, guest, massageTextField.getText());
        ArrayList<String> input = new ArrayList<>();
        input.add(new Gson().toJson(massage));
        connectToServer(new RequestForServer("AccountAreaController", "sendMassage", getToken(), input));
        setScene("chatPage.fxml", "chat page");
    }

    public void backButton(ActionEvent actionEvent) {
        t.cancel();
        System.out.println("canceled");
        setScene(path, backTitle);
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
            t.cancel();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public static void setOwner(String owner) {
        ChatPageController.owner = owner;
    }

    public static void setGuest(String guest) {
        ChatPageController.guest = guest;
    }

    public static void setPath(String path) {
        ChatPageController.path = path;
    }

    public static void setBackTitle(String backTitle) {
        ChatPageController.backTitle = backTitle;
    }

    public void refreshPage(MouseEvent mouseEvent) {
        setScene("chatPage.fxml", "chat page");
    }

    public static void setvValue(double vValue) {
        ChatPageController.vValue = vValue;
    }
}
