package com.example.app.Controller.Admin.DetailStore;

import com.example.app.Models.Admin.DetailStore.ItemInDetailStoreModel;
import com.example.app.Models.Admin.Item;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemInDetailStore implements Initializable {

    public Text itemId;
    public Text itemName;
    public Text quantity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(ItemInDetailStoreModel item) {
        itemId.setText(item.getItemId());
        itemName.setText(item.getItemName());
        quantity.setText(String.valueOf(item.getQuantity()));
    }
}
