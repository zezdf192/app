package com.example.app.Controller.Admin.ManageStore;

import com.example.app.ConnectDB.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CreateStoreController implements Initializable {
    public TextField name_input;

    public TextField address_input;
    public Button create_btn;
    public TextField storeId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        create_btn.setOnAction(event -> createNewStore(event));
    }

    private boolean checkInval() {
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "SELECT COUNT(*) AS total FROM Store WHERE StoreId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, storeId.getText());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int total = resultSet.getInt("total");
                        return total > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Lỗi không xác định!", Alert.AlertType.ERROR);
        }

        return false;
    }

    private void createNewStore(ActionEvent event) {

        boolean check = checkInval();
        if(check) {
            showAlert("Lỗi", "Mã kho hàng đã tồn tại!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "INSERT INTO Store (StoreId, NameStore, AddressStore) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, storeId.getText());
                preparedStatement.setString(2, name_input.getText());
                preparedStatement.setString(3,  address_input.getText());
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
