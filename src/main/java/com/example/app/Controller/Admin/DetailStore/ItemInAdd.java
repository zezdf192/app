package com.example.app.Controller.Admin.DetailStore;

import com.example.app.Models.Admin.DetailIEModel;
import com.example.app.Models.Admin.Item;
import com.example.app.Models.Data;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemInAdd implements Initializable {

    public Text itemId;
    public Text itemName;
    public TextField quantity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateValueByKey(Data.getDataGLobal.dataGlobal.getCurrentDetailIE(), itemId.getText(), newValue);
            }
        });
    }

    public static void updateValueByKey(List<DetailIEModel> array, String key, String newValue) {
        if (newValue.equals("")) return;
        for (DetailIEModel example : array) {
            if (example.getItemId().equals(key)) {
                example.setQuantity(Integer.parseInt(newValue));
                break; // Nếu bạn muốn dừng lại sau khi tìm thấy phần tử có key tương ứng
            }
        }
        Data.getDataGLobal.dataGlobal.setCurrentDetailIE(array);
    }
    public void setData(Item item) {
        itemId.setText(item.getItemId());
        itemName.setText(item.getNameItem());
        quantity.setText("0");
    }
}
