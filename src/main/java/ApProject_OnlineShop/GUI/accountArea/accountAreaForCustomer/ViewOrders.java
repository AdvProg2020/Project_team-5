package ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class ViewOrders extends FxmlController {
    private AccountAreaForCustomerController GUIAccountAreaCustomer;

    public ViewOrders(AccountAreaForCustomerController GUIAccountAreaCustomer) {
        this.GUIAccountAreaCustomer = GUIAccountAreaCustomer;
    }

    public void viewSortedOrders(int sort){
        GridPane root = GUIAccountAreaCustomer.makeGridPane();
        Label topic = new Label("All Orders");
        topic.setFont(Font.font("Times New Roman", 26));
        topic.setPadding(new Insets(5));
        GridPane.setHalignment(topic, HPos.CENTER);
        root.add(topic, 1, 1);
        VBox vBox = new VBox();
        GUIAccountAreaCustomer.setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        List<String> orders = MainController.getInstance().getAccountAreaForCustomerController().getSortedCustomerOrders(sort);
        for (String order : orders) {
            Hyperlink discountLink = new Hyperlink(order);
            discountLink.setOnMouseClicked(e -> viewSingleOrder(order));
            discountLink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            discountLink.setAlignment(Pos.BOTTOM_LEFT);
            discountLink.setPadding(new Insets(8));
            discountLink.setUnderline(false);
            vBox.getChildren().add(discountLink);
        }
        MenuItem sortByDiscountPercent = new MenuItem("sort by date");
        MenuItem sortByEndDate = new MenuItem("sort by price");
        sortByDiscountPercent.setOnAction(e -> viewSortedOrders(1));
        sortByEndDate.setOnAction(e -> viewSortedOrders(2));
        GUIAccountAreaCustomer.setMenuItems(sortByDiscountPercent);
        GUIAccountAreaCustomer.setMenuItems(sortByEndDate);
        MenuButton sorts = new MenuButton("sorts", null, sortByDiscountPercent, sortByEndDate);
        GUIAccountAreaCustomer.setMenuButtonStyle(sorts);
        root.add(sorts, 0, 2);
        StageController.setSceneJavaFx(root);
    }

    public void viewSingleOrder(String orderString){
        int index = orderString.indexOf("  ");
        String code = orderString.substring("order ID: ".length(), index);
        long orderId = Long.parseLong(code);
        List<String> orderDetails = ((Customer)MainController.getInstance().getCurrentPerson()).findOrderById(orderId).getDetails();
        GridPane root = GUIAccountAreaCustomer.makeGridPane();
        Label discountCodeInfo = new Label("Customer Order");
        discountCodeInfo.setFont(Font.font("Times New Roman", 26));
        discountCodeInfo.setPadding(new Insets(7));
        GridPane.setHalignment(discountCodeInfo, HPos.CENTER);
        root.add(discountCodeInfo, 1, 1);
        VBox vBox = new VBox();
        addDetailsToVBox(orderDetails, vBox);
        GUIAccountAreaCustomer.setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        vBox.setMinHeight(400);
        vBox.setMinWidth(600);
        vBox.setSpacing(20);
        root.getChildren().get(0).setOnMouseClicked(e -> viewSortedOrders(0));
        StageController.setSceneJavaFx(root);
    }

    public void addDetailsToVBox(List<String> orderDetails, VBox vBox){
        Label id = new Label("order ID:     " + orderDetails.get(0));
        Label date = new Label("date:     " + orderDetails.get(1));
        Label goodsList = new Label("goods list:\n" + orderDetails.get(2));
        Label discountAmount = new Label("discount amount:     " + orderDetails.get(3));
        Label postCode = new Label("post code:     " + orderDetails.get(4));
        Label address = new Label("address:     " + orderDetails.get(5));
        Label phoneNumber = new Label("phone number:     " + orderDetails.get(6));
        Label orderStatus = new Label("order status:     " + orderDetails.get(7));
        goodsList.setPadding(new Insets(7));
        GUIAccountAreaCustomer.setTextFont(id);
        GUIAccountAreaCustomer.setTextFont(date);
        GUIAccountAreaCustomer.setTextFont(goodsList);
        GUIAccountAreaCustomer.setTextFont(discountAmount);
        GUIAccountAreaCustomer.setTextFont(postCode);
        GUIAccountAreaCustomer.setTextFont(address);
        GUIAccountAreaCustomer.setTextFont(phoneNumber);
        GUIAccountAreaCustomer.setTextFont(orderStatus);
        vBox.getChildren().add(id);
        vBox.getChildren().add(date);
        vBox.getChildren().add(goodsList);
        vBox.getChildren().add(discountAmount);
        vBox.getChildren().add(postCode);
        vBox.getChildren().add(address);
        vBox.getChildren().add(phoneNumber);
        vBox.getChildren().add(orderStatus);
    }
}
