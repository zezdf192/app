package com.example.app.Controller.Admin.Censorship;

import com.example.app.Models.Admin.DetailIEModel;
import com.example.app.Models.Admin.ItemStore;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemVatTuController implements Initializable {
    public Text nameIE;
    public Text quantity;
    public Text IEId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(DetailIEModel detailIEModel) {
        nameIE.setText(detailIEModel.getItemId());
        IEId.setText(detailIEModel.getIEId());
        quantity.setText(String.valueOf(detailIEModel.getQuantity()));
    }
}
