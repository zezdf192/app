package com.example.app.Controller.Client.Check;

import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.User.IE;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckItemController implements Initializable {

    public Label IEId;
    public Label store;
    public Label time;
    public Label roleIE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(IE ie) {
        IEId.setText(ie.getIEId());
        store.setText(ie.getStoreId());
        time.setText(ie.getTimeIE());
        roleIE.setText(ie.getRoleIE());
    }
}
