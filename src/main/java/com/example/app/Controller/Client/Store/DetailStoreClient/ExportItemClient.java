package com.example.app.Controller.Client.Store.DetailStoreClient;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Controller.Admin.DetailStore.ItemInAdd;
import com.example.app.Models.Admin.DetailIEModel;
import com.example.app.Models.Admin.DetailStore.ItemInDetailStoreModel;
import com.example.app.Models.Admin.Item;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ExportItemClient implements Initializable {
    public Button export_btn;
    public VBox vbox;

    private List<Item> listItem = new ArrayList<>();

    private List<ItemInDetailStoreModel> listItemInDetailStore = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getItem();
        renderListItem();
        callAPI();
        export_btn.setOnAction(event -> handleExportItemInStore());
    }

    private void renderListItem() {
        for (int i = 0; i < listItem.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Admin/DetailStore/ItemInAdd.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                ItemInAdd cic = fxmlLoader.getController();
                cic.setData(listItem.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getItem() {
        try{
            Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM Item";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                //  String origin, int quantity, String img, float price, String desItem
                String itemId = resultSet.getString("ItemId");
                String roleItem = resultSet.getString("RoleItem");
                String nameItem = resultSet.getString("NameItem");
                String origin = resultSet.getString("Origin");
                String img = resultSet.getString("Img");

                String desItem = resultSet.getString("DesItem");

                Item newItem = new Item(itemId, roleItem, nameItem, origin, img, desItem);
                listItem.add(newItem);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomStringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomStringBuilder.append(randomChar);
        }

        return randomStringBuilder.toString();
    }


    private boolean updateDBDetailIE(String randomString) {

        for (DetailIEModel IE : Data.getDataGLobal.dataGlobal.getCurrentDetailIE()) {
            if(IE.getQuantity() == 0) {
                continue;
            }
            int absoluteInt = -IE.getQuantity();

            try (Connection connection = ConnectDB.getConnection()) {

                String sql = "INSERT INTO DetailIE (IEId, ItemId, Quantity) VALUES (?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, randomString);
                    preparedStatement.setString(2, IE.getItemId());
                    preparedStatement.setInt(3,  absoluteInt);
                    int rowsAffected = preparedStatement.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Lỗi không xác định!", Alert.AlertType.ERROR);
            }
        }
        return true;

    }

    private boolean checkConditions() {
        for (DetailIEModel IE : Data.getDataGLobal.dataGlobal.getCurrentDetailIE()) {
            if(IE.getQuantity() < 0) {
                showAlert("Lỗi", "Không được nhập số âm!", Alert.AlertType.ERROR);
                return false;
            }

            for (ItemInDetailStoreModel x : listItemInDetailStore) {
                if(x.getItemId().equals(IE.getItemId())) {
                    if(x.getQuantity() < IE.getQuantity()) {
                        showAlert("Lỗi", IE.getItemId() + " không được lấy quá số hàng trong kho", Alert.AlertType.ERROR);
                        return false;
                    }
                    break;
                }
            }

        }

        return true;
    }


    private void handleExportItemInStore() {
        String randomString = generateRandomString(6);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        Date sqlDate = Date.valueOf(currentDate);

        ItemStore store = Data.getDataGLobal.dataGlobal.getCurrentEditStore();

        boolean check = checkConditions();

        if(!check) {
            return;
        }

        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "INSERT INTO IE (IEId, TimeIE, StoreId, PersonId, RoleIE, TypeIE) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, randomString);
                preparedStatement.setString(2, String.valueOf(sqlDate));
                preparedStatement.setString(3,  store.getStoreId());
                preparedStatement.setString(4,  "P1");
                preparedStatement.setString(5,  "C2");
                preparedStatement.setString(6,  "Xuất hàng");
                int rowsAffected = preparedStatement.executeUpdate();
            }
            updateDBDetailIE(randomString); //Check insert DetailIE

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Lỗi không xác định!", Alert.AlertType.ERROR);
        }



        Stage stage = (Stage) export_btn.getScene().getWindow();
        stage.close();
    }

    public void callAPI() {
        String storeId = Data.getDataGLobal.dataGlobal.getCurrentEditStore().getStoreId();
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "SELECT item.ItemId, item.NameItem, SUM(DetailIE.Quantity) AS TotalQuantity\n" +
                    "FROM DetailIE\n" +
                    "JOIN IE ON DetailIE.IEId = IE.IEId\n" +
                    "JOIN Item ON DetailIE.ItemId = Item.ItemId\n" +
                    "WHERE IE.RoleIE = ? and IE.StoreId = ?\n" +
                    "GROUP BY item.ItemId, item.NameItem";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "C1");
                preparedStatement.setString(2, storeId);
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

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
