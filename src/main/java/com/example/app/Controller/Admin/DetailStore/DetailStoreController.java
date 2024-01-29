package com.example.app.Controller.Admin.DetailStore;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.DetailStore.ItemInDetailStoreModel;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailStoreController implements Initializable {

    public Text nameStore;
    public Text addressStore;
    public Button addItem;
    public Button find_btn;
    public TextField text_find;
    public ComboBox find_comboBox;
    public ComboBox sort_comboBox;
    public VBox vbox;
    private List<ItemInDetailStoreModel> listItemInDetailStore = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> role = FXCollections.observableArrayList(
                "Tìm kiếm theo mã vật tư", "Tìm kiếm theo tên vật tư"
        );
        ObservableList<String> range = FXCollections.observableArrayList(
                "Sắp xếp tăng dần theo mã vật tư","Sắp xếp giảm dần theo mã vật tư",
                "Sắp xếp tăng dần theo số lượng", "Sắp xếp giảm dần theo số lượng"
        );
        find_comboBox.setItems(role);
        find_comboBox.setValue("Tìm kiếm theo mã vật tư");

        sort_comboBox.setItems(range);
        sort_comboBox.setValue("Không sắp xếp");


        updateInfoStore();
        callAPI();
        renderListItem();
        find_btn.setOnAction(event -> findData());

        addItem.setOnAction(event -> handleCreateNewItem());
    }

    private void updateInfoStore() {
        ItemStore itemStore = Data.getDataGLobal.dataGlobal.getCurrentEditStore();

        nameStore.setText("Tên kho: " + itemStore.getNameStore());
        addressStore.setText("Địa chỉ: " + itemStore.getAddressStore());
    }

    private void handleCreateNewItem() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/DetailStore/CreateNewItemInStore.fxml"));
        Stage createUserStage = new Stage();
        createStage(loader, createUserStage);

        createUserStage.setOnHiding(event -> {
            updateData();
        });
    }

    private void updateData() {
        vbox.getChildren().clear();
        listItemInDetailStore.clear();
        callAPI();
        renderListItem();
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

    public void callAPI() {
        String storeId = Data.getDataGLobal.dataGlobal.getCurrentEditStore().getStoreId();
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "SELECT item.ItemId, item.NameItem, SUM(DetailIE.Quantity) AS TotalQuantity\n" +
                    "FROM DetailIE\n" +
                    "JOIN IE ON DetailIE.IEId = IE.IEId\n" +
                    "JOIN Item ON DetailIE.ItemId = Item.ItemId\n" +
                    "WHERE IE.StoreId = ?\n" +
                    "group by item.ItemId, item.NameItem";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, storeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String itemId = resultSet.getString("ItemId");
                        String itemName = resultSet.getString("NameItem");
                        int quantity = resultSet.getInt("TotalQuantity");
                        ItemInDetailStoreModel itemInDetailStore = new ItemInDetailStoreModel(itemId, itemName, quantity);
                        listItemInDetailStore.add(itemInDetailStore);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void renderListItem() {
        for (int i = 0; i < listItemInDetailStore.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Admin/DetailStore/ItemInDetailStore.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                ItemInDetailStore cic = fxmlLoader.getController();
                cic.setData(listItemInDetailStore.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getTypeFind() {
        String typeFind;
        if (find_comboBox.getValue().equals("Tìm kiếm theo tên vật tư")) {
            typeFind = "Item.NameItem";
        } else {
            typeFind = "Item.ItemId";
        }
        return typeFind;
    }

    public String getTypeRange() {

        String typeRange;
        if (sort_comboBox.getValue().equals("Sắp xếp tăng dần theo mã vật tư")) {
            typeRange = "ORDER BY ItemId ASC";
        }else if (sort_comboBox.getValue().equals("Sắp xếp giảm dần theo mã vật tư")) {
            typeRange = "ORDER BY ItemId DESC";
        } else if (sort_comboBox.getValue().equals("Sắp xếp tăng dần theo số lượng")) {
            typeRange = "ORDER BY TotalQuantity ASC";
        } else if (sort_comboBox.getValue().equals("Sắp xếp giảm dần theo số lượng")) {
            typeRange = "ORDER BY TotalQuantity DESC";
        } else {
            typeRange = "ORDER BY ItemId ASC";
        }

        return typeRange;
    }

    private void findData() {
        String storeId = Data.getDataGLobal.dataGlobal.getCurrentEditStore().getStoreId();
        String typeFind = getTypeFind();
        String typeRange = getTypeRange();

        try {
            String text = text_find.getText();
            //String query = "SELECT * FROM Store where " + typeFind + " LIKE ? " + typeRange;
            String query = "SELECT item.ItemId, item.NameItem, SUM(DetailIE.Quantity) AS TotalQuantity\n" +
                    "FROM DetailIE\n" +
                    "JOIN IE ON DetailIE.IEId = IE.IEId\n" +
                    "JOIN Item ON DetailIE.ItemId = Item.ItemId\n" +
                    "WHERE IE.StoreId = ? and "  + typeFind + " LIKE ? " +
                    " group by item.ItemId, item.NameItem " + typeRange ;

            Connection connection = ConnectDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, storeId);
            preparedStatement.setString(2, "%" + text + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            listItemInDetailStore.clear();

            while (resultSet.next()) {
                String itemId = resultSet.getString("ItemId");
                String itemName = resultSet.getString("NameItem");
                int quantity = resultSet.getInt("TotalQuantity");
                ItemInDetailStoreModel itemInDetailStore = new ItemInDetailStoreModel(itemId, itemName, quantity);
                listItemInDetailStore.add(itemInDetailStore);
            }
            vbox.getChildren().clear();
            renderListItem();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
