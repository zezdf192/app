package com.example.app.Controller.Client.Store;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Controller.Admin.ManageStore.ItemStoreController;
import com.example.app.Models.Admin.ItemStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Store implements Initializable {
    public VBox vbox;
    public TextField text_find;
    public ComboBox find_comboBox;
    public Button find_btn;

    public List<ItemStore> listStore = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> role = FXCollections.observableArrayList(
                "Tìm kiếm theo mã kho", "Tìm kiếm theo tên kho", "Tìm kiếm theo địa chỉ kho"
        );

        find_comboBox.setItems(role);
        find_comboBox.setValue("Tìm kiếm theo mã kho");

        callAPI();
        renderListUser();
        find_btn.setOnAction(event -> findData());
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
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Client/Store/ItemStoreClient.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                ItemStoreClient cic = fxmlLoader.getController();
                cic.setData(listStore.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void findData() {
        String typeFind = getTypeFind();

        try {
            String text = text_find.getText();
            String query = "SELECT * FROM Store where " + typeFind + " LIKE ? " ;

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
