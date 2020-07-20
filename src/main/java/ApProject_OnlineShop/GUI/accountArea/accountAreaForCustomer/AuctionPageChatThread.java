package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import java.util.ArrayList;

public class AuctionPageChatThread extends FxmlController implements Runnable {
    private AuctionPageController controller;
    private String selectedAuctionId;
    int numberOfChats;
    private boolean exit;

    public AuctionPageChatThread(AuctionPageController controller, String selectedAuctionId) {
        this.selectedAuctionId = selectedAuctionId;
        this.controller = controller;
        this.exit = false;
    }

    @Override
    public void run() {
        long pastTime = System.currentTimeMillis();
        while (true) {
            long test = System.currentTimeMillis();
            if (test >= (pastTime + 2 * 1000)) { //multiply by 1000 to get milliseconds
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(selectedAuctionId);
                ArrayList<Massage> massages = new Gson().fromJson(connectToServer(new RequestForServer("AuctionsController", "getMassages", getToken(), inputs)), new TypeToken<ArrayList<Massage>>() {
                }.getType());
                if (massages.size() > numberOfChats) {
                    numberOfChats = massages.size();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.updateChatBox();
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
