package com.example.app.Controller.Admin;

import com.example.app.Models.Model;
import com.example.app.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button manage_user;
    public Button manage_store;
    public Button manage_item;
    public Button manage_other;
    public Button censorship_btn;
    public Button statistical;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manage_user.setOnAction(event -> manage_user());
        manage_store.setOnAction(event -> manage_store());
        manage_item.setOnAction(event -> manage_item());
    }

    private void manage_user () {
        Model.getInstance().getViewAdminFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.MANAGE_USER);
    }
    private void manage_store () {
        Model.getInstance().getViewAdminFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.MANAGE_STORE);
    }
    private void manage_item () {
        Model.getInstance().getViewAdminFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.MANAGE_ITEM);
    }
}
