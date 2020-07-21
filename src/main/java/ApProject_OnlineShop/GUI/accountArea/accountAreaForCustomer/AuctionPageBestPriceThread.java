package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.application.Platform;

import java.util.ArrayList;

public class AuctionPageBestPriceThread extends FxmlController implements Runnable {
    private AuctionPageController controller;
    private String selectedAuctionId;
    String bestPriceOld;
    private boolean exit;

    public AuctionPageBestPriceThread(AuctionPageController controller, String selectedAuctionId) {
        this.selectedAuctionId = selectedAuctionId;
        this.controller = controller;
        this.exit = false;
        this.bestPriceOld = "";
    }

    @Override
    public void run() {
        long pastTime = System.currentTimeMillis();
        while (true) {
            long test = System.currentTimeMillis();
            if (test >= (pastTime + 2 * 1000)) { //multiply by 1000 to get milliseconds
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(selectedAuctionId);
                String bestPrice = connectToServer(new RequestForServer("AuctionsController", "getBestPriceOfAuction", getToken(), inputs));
                if (!bestPrice.equals(bestPriceOld)) {
                    bestPriceOld = bestPrice;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.updateBestPrice();
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
