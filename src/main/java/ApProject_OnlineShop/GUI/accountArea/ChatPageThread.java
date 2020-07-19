package ApProject_OnlineShop.GUI.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import java.util.ArrayList;

public class ChatPageThread extends FxmlController implements Runnable {
    private ChatPageController controller;
    int numberOfChats;
    private String owner;
    private String guest;
    private boolean exit;

    public ChatPageThread(ChatPageController controller, String owner, String guest) {
        this.controller = controller;
        this.owner = owner;
        this.guest = guest;
        this.exit = false;
    }

    @Override
    public void run() {
        long pastTime = System.currentTimeMillis();
        while (true) {
            long test = System.currentTimeMillis();
            if (test >= (pastTime + 2 * 1000)) { //multiply by 1000 to get milliseconds
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(owner);
                inputs.add(guest);
                ArrayList<Massage> massages = new Gson().fromJson(connectToServer(new RequestForServer("AccountAreaForCustomerController", "getMassages", getToken(), inputs)), new TypeToken<ArrayList<Massage>>() {
                }.getType());
                if (massages.size() > numberOfChats) {
                    numberOfChats = massages.size();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.loadChats();
                        }
                    });
                }
                pastTime = test;
            }
            if (exit) {
                System.out.println("canceled!!!!!");
                break;
            }
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
