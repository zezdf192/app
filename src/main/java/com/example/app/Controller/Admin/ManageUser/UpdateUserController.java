package com.example.app.Controller.Admin.ManageUser;

//import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.app.ConnectDB.ConnectDB;

import com.example.app.Models.Data;
import com.example.app.Models.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {


    public TextField name_input;
    public TextField email_input;
    public TextField password_input;


    public TextField id_user;
    public TextField address;
    public TextField phone;
    public Button update_btn;

    public UpdateUserController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        update_btn.setOnAction(event -> updateUser(event));

        updateInfoUser();
    }

    private void updateInfoUser() {
        User user = Data.getDataGLobal.dataGlobal.getCurrentUser();
        name_input.setText(user.getUserName());
        email_input.setText(user.getEmail());
        password_input.setText(user.getPassword());
        id_user.setText(user.getUserId());
        address.setText(user.getAddress());
        phone.setText(user.getPhoneNumber());
    }

    private void updateUser(ActionEvent event) {

        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "UPDATE person SET PasswordAcc = ?, NamePerson = ?, Email = ?, PhoneNumber = ?, AddressPerson = ? WHERE PersonId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, password_input.getText());
                preparedStatement.setString(2, name_input.getText());
                preparedStatement.setString(3, email_input.getText());
                preparedStatement.setString(4, phone.getText());
                preparedStatement.setString(5, address.getText());
                preparedStatement.setString(6, id_user.getText());
                int rowsAffected = preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = new User(id_user.getText(), email_input.getText(), name_input.getText(), password_input.getText(), phone.getText(), address.getText(), "R2");
        Data.getDataGLobal.dataGlobal.setCurrentUser(user);
        Stage stage = (Stage) update_btn.getScene().getWindow();
        stage.close();
    }
//    private String hashPassword(String password) {
//        // Hash the password using BCrypt
//        String bcryptResult = BCrypt.withDefaults().hashToString(12, password.toCharArray());
//        return bcryptResult.toString();
//    }
}
