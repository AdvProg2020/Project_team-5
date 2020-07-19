package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.util.ArrayList;

public class ChatPageThread extends FxmlController implements Runnable {
    private ChatPageController controller;
    int numberOfChats;
    private String owner;
    private String guest;
    private ScrollPane scrollPane;

    public ChatPageThread(ChatPageController controller, String owner, String guest, ScrollPane scrollPane) {
        this.controller = controller;
        this.scrollPane = scrollPane;
        this.owner = owner;
        this.guest = guest;
    }

    @Override
    public void run() {
        long pastTime = System.currentTimeMillis();
        while (true) {
            long test = System.currentTimeMillis();
            if (test >= (pastTime + 5 * 1000)) { //multiply by 1000 to get milliseconds
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(owner);
                inputs.add(guest);
                ArrayList<Massage> massages = new Gson().fromJson(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getMassages", getToken(), inputs)), new TypeToken<ArrayList<Massage>>() {
                }.getType());
                if (massages.size() > numberOfChats) {
                    pastTime = test;
                    ChatPageController.setvValue(scrollPane.getVvalue());
                    System.out.println("hi");
                    numberOfChats = massages.size();
                    controller.massages = massages;
                }
            }
        }
    }
}
