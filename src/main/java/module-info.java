module ApProject.OnlineShop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires yagson;

    opens ApProject_OnlineShop;
    opens ApProject_OnlineShop.GUI;
    opens ApProject_OnlineShop.GUI.mainMenu;
    opens ApProject_OnlineShop.GUI.loginRegister;
    exports ApProject_OnlineShop;
}