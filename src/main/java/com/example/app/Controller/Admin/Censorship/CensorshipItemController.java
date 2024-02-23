package com.example.app.Controller.Admin.Censorship;

import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import com.example.app.Models.Model;
import com.example.app.Models.User.IE;
import com.example.app.Views.AdminMenuOptions;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CensorshipItemController implements Initializable {
    public Label IEId;
    public Label store;
    public Label time;

    public HBox hbox;
    public Button detail_btn;
    private String personId;
    private String roleIE;
    private String typeIE;
    private String timeIE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detail_btn.setOnAction(event -> handleCensorship());
    }

    public void setData(IE ie) {
        IEId.setText(ie.getIEId());
        store.setText(ie.getStoreId());
        time.setText(ie.getRoleIE());
        personId = ie.getPersonId();
        roleIE = ie.getRoleIE();
        typeIE = ie.getTypeIE();
        timeIE = ie.getTimeIE();
    }

    private void openDetailIEView() {

    }

    private void handleCensorship() {
        //, String personId, String storeId, String roleIE, String typeIE
        IE ie = new IE(IEId.getText(), timeIE, personId, store.getText(), roleIE, typeIE);
        Data.getDataGLobal.dataGlobal.setCurrentIE(ie);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Censorship/DetailIE.fxml"));
        Stage createUserStage = new Stage();
        createStage(loader, createUserStage);

        createUserStage.setOnHiding(event -> {
            updateData();
        });
    }

    private void updateData() {
        IE ie = Data.getDataGLobal.dataGlobal.getCurrentIE();
        IEId.setText(ie.getIEId());
        store.setText(ie.getStoreId());
        time.setText(ie.getRoleIE());
        personId = ie.getPersonId();
        roleIE = ie.getRoleIE();
        typeIE = ie.getTypeIE();
        timeIE = ie.getTimeIE();
    }

    public void createStage(FXMLLoader loader, Stage stage) {
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Censorship");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
