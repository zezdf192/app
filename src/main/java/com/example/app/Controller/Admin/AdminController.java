package com.example.app.Controller.Admin;

import com.example.app.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewAdminFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case MANAGE_STORE:
                    admin_parent.setCenter(Model.getInstance().getViewAdminFactory().getManageUserView());
                    break;
                default:

                    break;
            }
        });
    }
}
