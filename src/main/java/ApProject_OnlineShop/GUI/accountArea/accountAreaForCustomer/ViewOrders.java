package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ViewOrders extends FxmlController {
    private AccountAreaForCustomerController GUIAccountAreaCustomer;

    public ViewOrders(AccountAreaForCustomerController GUIAccountAreaCustomer) {
        this.GUIAccountAreaCustomer = GUIAccountAreaCustomer;
    }

    public void viewOrders(){
        GridPane root = GUIAccountAreaCustomer.makeGridPane();
        Label topic = new Label("All Orders");
        topic.setFont(Font.font("Times New Roman", 26));
        topic.setPadding(new Insets(20));
        GridPane.setHalignment(topic, HPos.CENTER);
        root.add(topic, 1, 1);
        VBox vBox = new VBox();
        GUIAccountAreaCustomer.setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        StageController.setSceneJavaFx(root);
    }
}
