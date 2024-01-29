package com.example.app.Controller.Admin.ManageStore;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.ItemStore;
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

public class ManageStoreController implements Initializable {
    public ComboBox arrange_comboBox;
    public TextField text_find;
    public ComboBox find_comboBox;
    public Button find_btn;
    public VBox vbox;
    public List<ItemStore> listStore = new ArrayList<>();
    public Button add_store;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> role = FXCollections.observableArrayList(
                "Tìm kiếm theo mã kho", "Tìm kiếm theo tên kho", "Tìm kiếm theo địa chỉ kho"
        );
        ObservableList<String> range = FXCollections.observableArrayList(
                "Sắp xếp tăng dần theo mã kho","Sắp xếp giảm dần theo mã kho",
                "Sắp xếp tăng dần theo tên kho",  "Sắp xếp giảm dần theo tên kho",
                "Sắp xếp tăng dần theo địa chỉ", "Sắp xếp giảm dần theo địa chỉ"
        );
        find_comboBox.setItems(role);
        find_comboBox.setValue("Tìm kiếm theo mã kho");

        arrange_comboBox.setItems(range);
        arrange_comboBox.setValue("Sắp xếp tăng dần theo mã kho");

        callAPI();
        renderListUser();
        find_btn.setOnAction(event -> findData());
        add_store.setOnAction(event -> handleCreateNewStore());
    }

    public void callAPI() {
        try{
            Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM Store";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nameStore = resultSet.getString("NameStore");
                String storeId = resultSet.getString("StoreId");
                String addressStore = resultSet.getString("AddressStore");

                ItemStore itemStore = new ItemStore(storeId, nameStore, addressStore);
                listStore.add(itemStore);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderListUser() {
        for (int i = 0; i < listStore.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Admin/ManageStore/ItemStore.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                ItemStoreController cic = fxmlLoader.getController();
                cic.setData(listStore.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCreateNewStore() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageStore/CreateStore.fxml"));
        Stage createUserStage = new Stage();
        createStage(loader, createUserStage);

        createUserStage.setOnHiding(event -> {
            updateData();
        });
    }

    private void updateData() {
        vbox.getChildren().clear();
        listStore.clear();
        callAPI();
        find_btn.setOnAction(event -> findData());

        renderListUser();
    }

    public void createStage(FXMLLoader loader, Stage stage) {
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Create new store");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTypeFind() {
        String typeFind;
        if (find_comboBox.getValue().equals("Tìm kiếm theo tên kho")) {
            typeFind = "NameStore";
        } else if (find_comboBox.getValue().equals("Tìm kiếm theo địa chỉ kho")) {
            typeFind = "AddressStore";
        } else {
            typeFind = "StoreId";
        }
        return typeFind;
    }

    public String getTypeRange() {

        String typeRange;
        if (arrange_comboBox.getValue().equals("Sắp xếp tăng dần theo tên kho")) {
            typeRange = "ORDER BY NameStore ASC";
        }else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo tên kho")) {
            typeRange = "ORDER BY NameStore DESC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp tăng dần theo địa chỉ kho")) {
            typeRange = "ORDER BY AddressStore ASC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo địa chỉ kho")) {
            typeRange = "ORDER BY AddressStore DESC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo mã kho")) {
            typeRange = "ORDER BY StoreId DESC";
        }else {
            typeRange = "ORDER BY StoreId ASC";
        }

        return typeRange;
    }

    private void findData() {
        String typeFind = getTypeFind();
        String typeRange = getTypeRange();


        try {
            String text = text_find.getText();
            String query = "SELECT * FROM Store where " + typeFind + " LIKE ? " + typeRange;

            Connection connection = ConnectDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + text + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            listStore.clear();

            while (resultSet.next()) {
                String storeId = resultSet.getString("StoreId");
                String name = resultSet.getString("NameStore");
                String address = resultSet.getString("AddressStore");


                ItemStore store = new ItemStore(storeId, name, address);
                listStore.add(store);
            }
            vbox.getChildren().clear();
            renderListUser();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
