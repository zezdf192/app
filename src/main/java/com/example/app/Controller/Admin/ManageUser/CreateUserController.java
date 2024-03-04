package com.example.app.Controller.Admin.ManageUser;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {


    public TextField name_input;
    public TextField email_input;
    public TextField password_input;
    public TextField repeat_pass_input;
    public TextField address;
    public Button create_btn;
    public ComboBox role_comboBox;
    public TextField id;
    public TextField phone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> role = FXCollections.observableArrayList(
                "Người dùng",  "Quản trị viên"
        );
        role_comboBox.setItems(role);
        role_comboBox.setValue("Người dùng");

        create_btn.setOnAction(event -> createNewUser(event));
    }

    private void createNewUser(ActionEvent event) {
        if(!password_input.getText().equals(repeat_pass_input.getText())) {
            showAlert("Lỗi", "Mật khẩu nhập lại chưa chính xác!", Alert.AlertType.ERROR);
            return;
        }
        String roleId;
        if(role_comboBox.getValue().equals("Người dùng")) {
            roleId = "R2";
        } else {
            roleId = "R1";
        }

        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "INSERT INTO person (PersonId, RolePerson, PasswordAcc, NamePerson, Email, PhoneNumber, AddressPerson) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, id.getText());
                preparedStatement.setString(2,roleId);
                preparedStatement.setString(3, password_input.getText());
                preparedStatement.setString(4, name_input.getText());
                preparedStatement.setString(5, email_input.getText());
                preparedStatement.setString(6, phone.getText());
                preparedStatement.setString(7, address.getText());
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

