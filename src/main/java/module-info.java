open module ApProject.OnlineShop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires yagson;
    requires com.google.gson;
    requires java.desktop;
    requires javafx.media;
    /*
    opens ApProject_OnlineShop;
    opens ApProject_OnlineShop.GUI;
    opens ApProject_OnlineShop.GUI.mainMenu;
    opens ApProject_OnlineShop.GUI.loginRegister;
    opens ApProject_OnlineShop.model.persons to com.google.gson;
    opens ApProject_OnlineShop.model.requests to com.google.gson;
    opens ApProject_OnlineShop.model to com.google.gson;
    opens ApProject_OnlineShop.model.orders;
    opens ApProject_OnlineShop.model.category;
    opens ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer;
    opens ApProject_OnlineShop.model.productThings;
    opens ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;
    opens ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;
    opens ApProject_OnlineShop.GUI.accountArea;
     */
    exports ApProject_OnlineShop;
}