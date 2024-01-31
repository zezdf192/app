package com.example.app.Controller.Admin.ManageItem;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Controller.Admin.ManageStore.ItemStoreController;
import com.example.app.Models.Admin.Item;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageItem implements Initializable {
    public VBox vbox;
    public Button add_item;
    public ComboBox arrange_comboBox;
    public ComboBox find_comboBox;
    public TextField text_find;
    public Button find_btn;

    public List<Item> listItem = new ArrayList<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_item.setOnAction(event -> handleCreateNewItem());


        ObservableList<String> role = FXCollections.observableArrayList(
                "Tìm kiếm theo mã vật dụng", "Tìm kiếm theo tên vât dụng", "Tìm kiếm theo xuất sứ"
        );
        ObservableList<String> range = FXCollections.observableArrayList(
                "Sắp xếp tăng dần theo mã vật dụng","Sắp xếp giảm dần theo mã vật dụng",
                "Sắp xếp tăng dần theo tên vật dụng",  "Sắp xếp giảm dần theo tên vật dụng",
                "Sắp xếp tăng dần theo xuất sứ", "Sắp xếp giảm dần theo xuất sứ"
        );
        find_comboBox.setItems(role);
        find_comboBox.setValue("Tìm kiếm theo mã vật dụng");

        arrange_comboBox.setItems(range);
        arrange_comboBox.setValue("Sắp xếp tăng dần theo mã vật dụng");

        callAPI();
        renderListItem();
        find_btn.setOnAction(event -> findData());
    }

    private void handleCreateNewItem() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageItem/CreateItemToStore.fxml"));
        Stage createUserStage = new Stage();
        createStage(loader, createUserStage);

        createUserStage.setOnHiding(event -> {
            updateData();
        });
    }

    public void createStage(FXMLLoader loader, Stage stage) {
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Create new Item");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void callAPI() {
        try{
            Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM Item";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String itemId = resultSet.getString("ItemId");
                String roleItem = resultSet.getString("RoleItem");
                String nameItem = resultSet.getString("NameItem");
                int quantityItem = resultSet.getInt("Quantity");
                String originItem = resultSet.getString("Origin");
                String imgItem = resultSet.getString("Img");
                String desItem = resultSet.getString("DesItem");

                Item itemItem = new Item(itemId, roleItem, nameItem, originItem, quantityItem, imgItem, desItem);
                listItem.add(itemItem);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderListItem() {
        for (int i = 0; i < listItem.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Admin/ManageItem/ItemItem.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                ItemItem cic = fxmlLoader.getController();
                cic.setData(listItem.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateData() {
        vbox.getChildren().clear();
        listItem.clear();
        callAPI();
        find_btn.setOnAction(event -> findData());

        renderListItem();
    }

    public String getTypeFind() {
        String typeFind;
        if (find_comboBox.getValue().equals("Tìm kiếm theo tên vật dụng")) {
            typeFind = "NameItem";
        } else if (find_comboBox.getValue().equals("Tìm kiếm theo xuất sứ")) {
            typeFind = "Origin";
        } else {
            typeFind = "ItemId";
        }
        return typeFind;
    }

    public String getTypeRange() {

        String typeRange;
        if (arrange_comboBox.getValue().equals("Sắp xếp tăng dần theo tên vật dụng")) {
            typeRange = "ORDER BY NameItem ASC";
        }else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo tên vật dụng")) {
            typeRange = "ORDER BY NameItem DESC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp tăng dần theo xuất sứ")) {
            typeRange = "ORDER BY Origin ASC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo xuất sứ")) {
            typeRange = "ORDER BY Origin DESC";
        } else if (arrange_comboBox.getValue().equals("Sắp xếp giảm dần theo mã vật dụng")) {
            typeRange = "ORDER BY ItemId DESC";
        }else {
            typeRange = "ORDER BY ItemId ASC";
        }

        return typeRange;
    }

    private void findData() {
        String typeFind = getTypeFind();
        String typeRange = getTypeRange();


        try {
            String text = text_find.getText();
            String query = "SELECT * FROM Item where " + typeFind + " LIKE ? " + typeRange;

            Connection connection = ConnectDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + text + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            listItem.clear();

            while (resultSet.next()) {
                String itemId = resultSet.getString("ItemId");
                String roleItem = resultSet.getString("RoleItem");
                String nameItem = resultSet.getString("NameItem");
                int quantityItem = resultSet.getInt("Quantity");
                String originItem = resultSet.getString("Origin");
                String imgItem = resultSet.getString("Img");
                String desItem = resultSet.getString("DesItem");

                Item itemItem = new Item(itemId, roleItem, nameItem, originItem, quantityItem, imgItem, desItem);
                listItem.add(itemItem);
            }
            vbox.getChildren().clear();
            renderListItem();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
