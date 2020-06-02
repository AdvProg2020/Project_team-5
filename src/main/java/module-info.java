module ApProject.OnlineShop {
    requires javafx.controls;
    requires javafx.fxml;
    requires yagson;

    opens ApProject_OnlineShop to javafx.fxml;
    opens ApProject_OnlineShop.GUI;
    opens ApProject_OnlineShop.GUI.mainMenu;
    exports ApProject_OnlineShop;
}