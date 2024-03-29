package com.example.app.Views;

import com.example.app.Controller.Admin.AdminController;
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

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
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
