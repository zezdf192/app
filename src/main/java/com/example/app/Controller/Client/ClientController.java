package com.example.app.Controller.Client;

import com.example.app.Models.Model;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController {
    public BorderPane client_parent;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case INFOACCOUNT:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getInfoAccountView());
                    break;
                case IMPORTITEM:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getImportItemView());
                    break;
                case EXPORTITEM:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getExportItemView());
                    break;
                case STORE:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getStoreView());
                    break;
                default:

                    break;
            }
        });
    }
}
