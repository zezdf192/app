package com.example.app.Controller.Admin.ManageItem;

import com.example.app.ConnectDB.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class CreateItemToStore implements Initializable {
    public TextField name_item;
    public TextField Id_item;
    public Button create_btn;
    public TextArea des_item;
    public TextField origin_item;
    public TextField role_item;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_btn.setOnAction(this::createNewItem);

        System.out.println(Id_item.getText());
    }


    private void createNewItem(ActionEvent event) {


        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "INSERT INTO Item (ItemId,RoleItem, NameItem, Origin,  Img, DesItem) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id_item.getText());
                preparedStatement.setString(2, role_item.getText());
                preparedStatement.setString(3, name_item.getText());
                preparedStatement.setString(4,  origin_item.getText());
                preparedStatement.setString(5,  "http://");
                preparedStatement.setString(6,  des_item.getText());

                int rowsAffected = preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Lỗi không xác định!", Alert.AlertType.ERROR);
        }
        Stage stage = (Stage) create_btn.getScene().getWindow();
        stage.close();



        //showAlert("Hoàn thành", "Tạo mới người dùng thành công!", Alert.AlertType.ERROR);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


