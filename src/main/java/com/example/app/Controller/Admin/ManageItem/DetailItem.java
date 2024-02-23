package com.example.app.Controller.Admin.ManageItem;

import com.example.app.Models.Admin.Item;
import com.example.app.Models.Data;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailItem implements Initializable {
    public Label name_Item;
    public Label id_Item;
    public Label quantity_Item;
    public Label origin_Item;
    public Label des_Item;
    public Label role_Item;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailInfoItem();
    }

    private void detailInfoItem () {
        Item item = Data.getDataGLobal.dataGlobal.getCurentEditItem();
        id_Item.setText(item.getItemId());
        role_Item.setText(item.getRoleItem());
        name_Item.setText(item.getNameItem());
        origin_Item.setText(item.getOrigin());
        des_Item.setText(item.getDesItem());
    }
}

