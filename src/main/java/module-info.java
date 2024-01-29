module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql;


    opens com.example.app to javafx.fxml;
    exports com.example.app;

    exports com.example.app.Controller;
    exports com.example.app.Controller.Client;
    exports com.example.app.Controller.Admin;
    exports com.example.app.Controller.Admin.ManageStore;
    exports com.example.app.Controller.Admin.DetailStore;
}