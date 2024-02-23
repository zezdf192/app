package com.example.app.Controller.Client;

import com.example.app.Models.Model;
import com.example.app.Views.AdminMenuOptions;
import com.example.app.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button manage_user;
    public Button playlist;
    public Button check;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manage_user.setOnAction(event -> viewInforUser());
        playlist.setOnAction(event -> viewStore());
        check.setOnAction(event -> viewCheck());
    }

    private void viewInforUser () {

        Model.getInstance().getViewClientFactory().getClientSelectedMenuItem().set(ClientMenuOptions.INFOACCOUNT);
    }

    private void viewStore() {
        Model.getInstance().getViewClientFactory().getClientSelectedMenuItem().set(ClientMenuOptions.STORE);
    }

    private void viewCheck() {
        Model.getInstance().getViewClientFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CHECK);
    }

}

