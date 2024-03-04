package com.example.app.Controller.Admin.ManageUser;

import com.example.app.ConnectDB.ConnectDB;

import com.example.app.Models.Data;
import com.example.app.Models.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ItemUserController implements Initializable {
    public Label id;
    public Label name;
    public Label email;

    public Button update_btn;
    public Button remove_btn;
    public Label phone;
    private String password;
    private String address;
    private String role;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        update_btn.setOnAction(event -> updateUser());
//        remove_btn.setOnAction(event -> removeUser(event));
    }

    public void setData(User user) {
        id.setText(String.valueOf(user.getUserId()));
        name.setText(user.getUserName());
        email.setText(user.getEmail());
        //role.setText(user.getRole());
        phone.setText(user.getPhoneNumber());
        password = user.getPassword();
        address= user.getAddress();
        role = user.getRole();
    }

    public void updateUser() {
        //String userId, String email, String userName, String password, String phoneNumber, String address, String role
        User user = new User(id.getText(), email.getText(), name.getText(), password, phone.getText(), address, role);
        Data.getDataGLobal.dataGlobal.setCurrentUser(user);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageUser/UpdateUser.fxml"));
            Stage createUserStage = new Stage();
            createStage(loader, createUserStage);
            createUserStage.setOnHiding(event -> {
                updateData();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateData() {
        User user = Data.getDataGLobal.dataGlobal.getCurrentUser();
        id.setText(String.valueOf(user.getUserId()));
        name.setText(user.getUserName());
        email.setText(user.getEmail());
        //role.setText(user.getRole());
        phone.setText(user.getPhoneNumber());
        password = user.getPassword();
        address= user.getAddress();
        role = user.getRole();

    }

    public void createStage(FXMLLoader loader, Stage stage) {
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Update new user");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeUser(ActionEvent event) {
        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "DELETE FROM user WHERE userId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Integer.valueOf(id.getText()));
                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BorderPane borderPane = (BorderPane) stage.getScene().getRoot();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/Admin/ManageUser/ManageUser.fxml"));
        Parent viewBottomClient;
        try {
            viewBottomClient = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        borderPane.setCenter(viewBottomClient);

    }
}
