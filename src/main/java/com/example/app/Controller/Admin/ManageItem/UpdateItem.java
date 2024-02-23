package com.example.app.Controller.Admin.ManageItem;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.Item;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
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
        update_btn.setOnAction(event -> updateItem(event));
    }

    private void updateInfoItem() {
        Item item = Data.getDataGLobal.dataGlobal.getCurentEditItem();
        Id_item.setText(item.getItemId());
        role_item.setText(item.getRoleItem());
        name_item.setText(item.getNameItem());
        origin_item.setText(item.getOrigin());
        des_item.setText(item.getDesItem());
    }

    private void updateItem(ActionEvent event) {

        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "UPDATE Item SET RoleItem = ?, NameItem = ?, Origin = ?, DesItem = ?, Img = ? WHERE ItemId = ?";


            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, role_item.getText());
                preparedStatement.setString(2, name_item.getText());
                preparedStatement.setString(3, origin_item.getText());
                preparedStatement.setString(4, des_item.getText());
                preparedStatement.setString(5, "https://");
                preparedStatement.setString(6, Id_item.getId());

                int rowsAffected = preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Item item = new Item(Id_item.getId(), role_item.getText(), name_item.getText(), origin_item.getText(), "http", des_item.getText());
        Data.getDataGLobal.dataGlobal.setCurrentEditItem(item);
        Stage stage = (Stage) update_btn.getScene().getWindow();
        stage.close();
    }
}
