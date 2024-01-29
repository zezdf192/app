package com.example.app.Controller.Admin.ManageStore;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;



public class UpdateStore implements Initializable {

    public TextField name_input;
    public TextField storeId;
    public Button update_btn;
    public TextField address_input;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateInfoStore();
        update_btn.setOnAction(event -> updateStore(event));
    }

    private void updateInfoStore() {
        ItemStore store = Data.getDataGLobal.dataGlobal.getCurrentEditStore();
        name_input.setText(store.getNameStore());
        storeId.setText(store.getStoreId());
        address_input.setText(store.getAddressStore());

    }

    private void updateStore(ActionEvent event) {

        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "UPDATE Store SET NameStore = ?, AddressStore = ? WHERE StoreId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name_input.getText());
                preparedStatement.setString(2, address_input.getText());
                preparedStatement.setString(3, storeId.getText());
                int rowsAffected = preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ItemStore store = new ItemStore(storeId.getText(), name_input.getText(), address_input.getText());
        Data.getDataGLobal.dataGlobal.setCurrentEditStore(store);
        Stage stage = (Stage) update_btn.getScene().getWindow();
        stage.close();
    }
}
