package com.example.app.Controller.Admin.ManageUser;

import com.example.app.ConnectDB.ConnectDB;
//import com.example.app.Models.Admin.ItemUser;
import com.example.app.Models.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUserController implements Initializable {
    Scene scene = null;

    public Button add_user;
    public ComboBox arrange_comboBox;
    public TextField text_find;
    public ComboBox find_comboBox;
    public Button find_btn;
    public VBox vbox;

    public List<User> listUser = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> role = FXCollections.observableArrayList(
                "Tìm kiếm theo mã", "Tìm kiếm theo tên"
        );
        ObservableList<String> range = FXCollections.observableArrayList(
                "Sắp xếp tăng dần theo mã","Sắp xếp giảm dần theo mã",
                "Sắp xếp tăng dần theo tên",  "Sắp xếp giảm dần theo tên"

        );
        find_comboBox.setItems(role);
        find_comboBox.setValue("Tìm kiếm theo mã");

        arrange_comboBox.setItems(range);
        arrange_comboBox.setValue("Không sắp xếp");

        callAPI();
        renderListUser();
        add_user.setOnAction(event -> handleCreateNewUser());
        find_btn.setOnAction(event -> findData());
    }

    public void callAPI() {
        try{
            Connection connection = ConnectDB.getConnection();
            String query = "select * from person join rolecode on rolecode.keycode = person.roleperson and rolecode.keycode <> 'R1'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("PersonId");
                String name = resultSet.getString("namePerson");
                String email = resultSet.getString("Email");
                String role = resultSet.getString("RolePerson");
                String phone = resultSet.getString("PhoneNumber");
                String address =  resultSet.getString("AddressPerson");
                String password =  resultSet.getString("PasswordAcc");
                //String userId, String email, String userName, String password, String phoneNumber, String address, String role
                User user = new User(id,email, name, password, phone, address, role);
                listUser.add(user);
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderListUser() {

        for (int i = 0; i < listUser.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Admin/ManageUser/ItemUser.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                ItemUserController cic = fxmlLoader.getController();
                cic.setData(listUser.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCreateNewUser() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageUser/CreateUser.fxml"));
        Stage createUserStage = new Stage();
        createStage(loader, createUserStage);

        createUserStage.setOnHiding(event -> {
            updateData();
        });
    }

    private void updateData() {
        vbox.getChildren().clear();
        listUser.clear();
        callAPI();
       renderListUser();
    }

    public void createStage(FXMLLoader loader, Stage stage) {
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Create new user");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTypeFind() {
        String typeFind;
        if (find_comboBox.getValue().equals("Tìm kiếm theo mã")) {
            typeFind = "PersonId";
        }  else {
            typeFind = "NamePerson";
        }
        return typeFind;
    }

    public String getTypeRange() {

        String typeRange;
        if (arrange_comboBox.getValue().equals("Sắp xếp tăng dần theo tên")) {
            typeRange = "ORDER BY NamePerson ASC";
        }else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo tên")) {
            typeRange = "ORDER BY NamePerson DESC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp tăng dần theo mã")) {
            typeRange = "ORDER BY PersonId ASC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo mã")) {
            typeRange = "ORDER BY PersonId DESC";
        } else {
            typeRange = "ORDER BY NamePerson ASC";
        }

        return typeRange;
    }
    private void findData() {
        String typeFind = getTypeFind();
        String typeRange = getTypeRange();


        try {

            String text = text_find.getText();
            String query = "select * from person join rolecode on rolecode.keycode = person.roleperson and rolecode.keycode <> 'R1' AND " + typeFind + " LIKE ? " + typeRange;

            Connection connection = ConnectDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + text + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            listUser.clear();


            while (resultSet.next()) {
                String id = resultSet.getString("PersonId");
                String name = resultSet.getString("namePerson");
                String email = resultSet.getString("Email");
                String role = resultSet.getString("RolePerson");
                String phone = resultSet.getString("PhoneNumber");
                String address = resultSet.getString("AddressPerson");
                String password = resultSet.getString("PasswordAcc");
                //String userId, String email, String userName, String password, String phoneNumber, String address, String role
                User user = new User(id, email, name, password, phone, address, role);
                listUser.add(user);

            }
            vbox.getChildren().clear();
            renderListUser();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
