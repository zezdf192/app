package com.example.app.Controller.Client;

import com.example.app.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Model.getInstance().getViewClientFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case INFOACCOUNT:
                    client_parent.setCenter(Model.getInstance().getViewClientFactory().getInfoAccountView());
                    break;
                case STORE:
                    client_parent.setCenter(Model.getInstance().getViewClientFactory().getStoreView());
                    break;
                case CHECK:
                    client_parent.setCenter(Model.getInstance().getViewClientFactory().getCheckView());
                    break;
                default:

                    break;
            }
        });
    }
}
