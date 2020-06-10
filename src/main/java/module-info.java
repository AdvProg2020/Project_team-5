module ApProject.OnlineShop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires yagson;
    requires com.google.gson;

    opens ApProject_OnlineShop;
    opens ApProject_OnlineShop.GUI;
    opens ApProject_OnlineShop.GUI.mainMenu;
    opens ApProject_OnlineShop.GUI.loginRegister;
    opens ApProject_OnlineShop.model.persons to com.google.gson;
    opens ApProject_OnlineShop.model.requests to com.google.gson;
    opens ApProject_OnlineShop.model;
    opens ApProject_OnlineShop.model.orders;
    opens ApProject_OnlineShop.model.category;
    opens ApProject_OnlineShop.GUI.accountArea;
    opens ApProject_OnlineShop.model.productThings;
    exports ApProject_OnlineShop;
}