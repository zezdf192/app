package com.example.app.Views;

import com.example.app.Controller.Admin.AdminController;
import com.example.app.Controller.Client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewClientFactory {
    Scene scene = null;
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane inforAccount;

    private AnchorPane storeView;

    private AnchorPane checkView;
    public ViewClientFactory() {
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }


    public AnchorPane getInfoAccountView() {

        if (inforAccount == null) {
            try {
                inforAccount = new FXMLLoader(getClass().getResource("/Fxml/Client/InfoAccount/InfoAccount.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inforAccount;
    }

    public AnchorPane getStoreView() {

        if (storeView == null) {
            try {
                storeView = new FXMLLoader(getClass().getResource("/Fxml/Client/Store/Store.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storeView;
    }

    public AnchorPane getCheckView() {

        if (checkView == null) {
            try {
                checkView = new FXMLLoader(getClass().getResource("/Fxml/Client/Check/Check.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return checkView;
    }


    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    public void createStage(FXMLLoader loader) {
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

}
