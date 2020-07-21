package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.GUI.accountArea.Styles;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class ViewOrdersForSeller extends FxmlController {
    private Styles style = new Styles();

    public void viewSortedOrders(int sort) {
        playButtonMusic();
        GridPane root = style.makeGridPane();
        Label topic = new Label("Sales History");
        topic.setFont(Font.font("Times New Roman", 26));
        topic.setPadding(new Insets(13));
        GridPane.setHalignment(topic, HPos.CENTER);
        VBox vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPrefWidth(430);
        vBox.setPrefWidth(420);
        scrollPane.setMinHeight(600);
        scrollPane.setMaxHeight(600);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        style.setVBoxStyle(vBox);
        root.add(scrollPane, 1, 2);
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("" + sort);
        List<String> orders = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getSortedLogs", getToken(), inputs)));
//        List<String> orders = MainController.getInstance().getAccountAreaForSellerController().getSortedLogs(sort);
        for (String order : orders) {
            Hyperlink discountLink = new Hyperlink(order);
            discountLink.setFont(new Font("Times New Roman", 16));
            discountLink.setPrefSize(400, 50);
            discountLink.setOnMouseClicked(e -> viewSingleOrder(order));
            discountLink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            discountLink.setAlignment(Pos.BOTTOM_LEFT);
            discountLink.setPadding(new Insets(8));
            discountLink.setUnderline(false);
            vBox.getChildren().add(discountLink);
        }
        List<String> fileOrders = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getFileOrdersOfSeller", getToken(), null)));
        for (String fileOrder : fileOrders) {
            Hyperlink discountLink = new Hyperlink(fileOrder);
            discountLink.setFont(new Font("Times New Roman", 16));
            discountLink.setPrefSize(400, 50);
            discountLink.setOnMouseClicked(e -> viewSingleFileOrder(fileOrder));
            discountLink.setStyle("-fx-text-fill: #250033; -fx-text-color: #250033;");
            discountLink.setAlignment(Pos.BOTTOM_LEFT);
            discountLink.setPadding(new Insets(8));
            discountLink.setUnderline(false);
            vBox.getChildren().add(discountLink);
        }
        if (((orders.size() + fileOrders.size())) * 50 > 600) {
            vBox.setPrefHeight(((orders.size() + fileOrders.size()) * 50) + 20);
        } else {
            vBox.setPrefHeight(600);
        }
        root.add(topic, 1, 1);
        MenuItem sortByDiscountPercent = new MenuItem("sort by date");
        MenuItem sortByEndDate = new MenuItem("sort by price");
        sortByDiscountPercent.setOnAction(e -> viewSortedOrders(1));
        sortByEndDate.setOnAction(e -> viewSortedOrders(2));
        style.setMenuItems(sortByDiscountPercent);
        style.setMenuItems(sortByEndDate);
        MenuButton sorts = new MenuButton("sorts", null, sortByDiscountPercent, sortByEndDate);
        style.setMenuButtonStyle(sorts);
        root.getChildren().get(0).setOnMouseClicked(e -> backToAccountAreaSeller());
        root.add(sorts, 0, 2);
        StageController.setSceneJavaFx(root);
    }

    public void viewSingleOrder(String orderString) {
        playButtonMusic();
        int index = orderString.indexOf("  ");
        String code = orderString.substring("order ID: ".length(), index);
        long orderId = Long.parseLong(code);
        ArrayList<String> inputs0 = new ArrayList<>();
        inputs0.add(orderId + "");
        inputs0.add(getCurrentPerson().getUsername());
        inputs0.add("seller");
        List<String> orderDetails = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaController", "getOrderDetails", getToken(), inputs0)));
//        List<String> orderDetails = ((Seller) FxmlController.getCurrentPerson()).findOrderById(orderId).getDetails();
        GridPane root = style.makeGridPane();
        Label discountCodeInfo = new Label("Seller Order");
        discountCodeInfo.setFont(Font.font("Times New Roman", 26));
        discountCodeInfo.setPadding(new Insets(7));
        GridPane.setHalignment(discountCodeInfo, HPos.CENTER);
        root.add(discountCodeInfo, 1, 1);
        VBox vBox = new VBox();
        addDetailsToVBox(orderDetails, vBox);
        style.setVBoxStyle(vBox);
        root.add(vBox, 1, 2);
        root.getChildren().get(0).setOnMouseClicked(e -> viewSortedOrders(0));
        StageController.setSceneJavaFx(root);
    }

    public void addDetailsToVBox(List<String> orderDetails, VBox vBox) {
        Label id = new Label("order ID:     " + orderDetails.get(0));
        Label date = new Label("date:     " + orderDetails.get(1));
        Label goodsList = new Label("goods list:\n\n" + orderDetails.get(2));
        Label discountAmount = new Label("discount amount:     " + orderDetails.get(3));
        Label totalAmount = new Label("total price:     " + orderDetails.get(4));
        Label orderStatus = new Label("order status:     " + orderDetails.get(5));
        goodsList.setPadding(new Insets(7));
        style.setTextFont(totalAmount);
        style.setTextFont(id);
        style.setTextFont(date);
        style.setTextFont(goodsList);
        style.setTextFont(discountAmount);
        style.setTextFont(orderStatus);
        vBox.getChildren().add(id);
        vBox.getChildren().add(date);
        vBox.getChildren().add(goodsList);
        vBox.getChildren().add(discountAmount);
        vBox.getChildren().add(totalAmount);
        vBox.getChildren().add(orderStatus);
    }

    public void viewSingleFileOrder(String order) {
        Long orderId = Long.parseLong(order.substring("file order Id: ".length(), order.indexOf("  ")));
        playButtonMusic();
        ArrayList<String> inputs0 = new ArrayList<>();
        inputs0.add(orderId + "");
//        inputs0.add(getCurrentPerson().getUsername());
//        inputs0.add("seller");
        List<String> orderDetails = convertStringToArraylist(connectToServer(new RequestForServer("AccountAreaForSellerController", "getFileOrderInfoGUI", getToken(), inputs0)));
        GridPane root = style.makeGridPane();
        Label discountCodeInfo = new Label("Seller File Order");
        discountCodeInfo.setFont(Font.font("Times New Roman", 26));
        discountCodeInfo.setPadding(new Insets(7));
        GridPane.setHalignment(discountCodeInfo, HPos.CENTER);
        root.add(discountCodeInfo, 1, 1);
        VBox vBox = new VBox();
        addDetailToFileOrderVBox(orderDetails, vBox);
        style.setVBoxStyle(vBox);
        vBox.setMinHeight(500);
        vBox.setMaxHeight(500);
        root.add(vBox, 1, 2);
        root.getChildren().get(0).setOnMouseClicked(e -> viewSortedOrders(0));
        StageController.setSceneJavaFx(root);
    }

    public void addDetailToFileOrderVBox(List<String> orderDetails, VBox vBox) {
        Label id = new Label("order ID:     " + orderDetails.get(0));
        Label date = new Label("date:     " + orderDetails.get(1));
        Label goodsList = new Label("file name:     " + orderDetails.get(2));
        Label discountAmount = new Label("customer name:     " + orderDetails.get(3));
        Label totalAmount = new Label("total price:     " + orderDetails.get(4));
        Label orderStatus = new Label("order status:     " + orderDetails.get(5));
        goodsList.setPadding(new Insets(7));
        style.setTextFont(totalAmount);
        style.setTextFont(id);
        style.setTextFont(date);
        style.setTextFont(goodsList);
        style.setTextFont(discountAmount);
        style.setTextFont(orderStatus);
        vBox.getChildren().add(id);
        vBox.getChildren().add(date);
        vBox.getChildren().add(goodsList);
        vBox.getChildren().add(discountAmount);
        vBox.getChildren().add(totalAmount);
        vBox.getChildren().add(orderStatus);
    }

    public void backToAccountAreaSeller() {
        setScene("accountAreaForSeller.fxml", "main menu");
    }
}
