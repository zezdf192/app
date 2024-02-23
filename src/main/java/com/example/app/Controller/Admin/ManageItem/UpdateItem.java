package com.example.app.Controller.Admin.ManageItem;

import com.example.app.Models.Admin.Item;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateItem implements Initializable {
    public TextField name_item;
    public TextField Id_item;
    public Button update_btn;
    public TextField origin_item;
    public TextField quantity_item;
    public TextArea des_item;
    public TextField role_item;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateInfoItem();
    }

    private void updateInfoItem() {
        Item item = Data.getDataGLobal.dataGlobal.getCurentEditItem();
        Id_item.setText(item.getItemId());
        role_item.setText(item.getRoleItem());
        name_item.setText(item.getNameItem());
        origin_item.setText(item.getOrigin());
        des_item.setText(item.getDesItem());
    }
}
