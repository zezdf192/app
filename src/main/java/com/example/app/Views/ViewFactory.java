package com.example.app.Views;

import com.example.app.Controller.Client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    Scene scene = null;
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;

    private AnchorPane infoAccountView;

    private AnchorPane importItemView;

    private AnchorPane exportItemView;

    private AnchorPane storeView;


    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }



    public AnchorPane getImportItemView() {
        if (importItemView == null) {
            try {
                importItemView = new FXMLLoader(getClass().getResource("/Fxml/Client/ImportItem/ImportItem.fxml")).load();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return importItemView;
    }

    public AnchorPane getExportItemView() {
        if (exportItemView == null) {
            try {
                exportItemView = new FXMLLoader(getClass().getResource("/Fxml/Client/ExportItem/ExportItem.fxml")).load();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exportItemView;
    }

    public AnchorPane getInfoAccountView() {
        if (infoAccountView == null) {
            try {
                infoAccountView = new FXMLLoader(getClass().getResource("/Fxml/Client/InfoAccount/InfoAccount.fxml")).load();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return infoAccountView;
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



    public Scene loadScene() {
        if (scene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
                scene = new Scene(loader.load());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void showSignupWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Signup.fxml"));
        createStage(loader);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
