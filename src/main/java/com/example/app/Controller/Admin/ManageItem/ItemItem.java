package com.example.app.Controller.Admin.ManageItem;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.Item;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ItemItem implements Initializable {
    public Label quantity_item;
    public Label name_item;
    public Label id_item;
    public Button update_btn;
    public Button remove_btn;
    public Button detail_btn;

    public String role_item;

    public String img_item;
    public String origin_item;

    public String des_item;
    public HBox hbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update_btn.setOnAction(event -> updateItem());
        remove_btn.setOnAction(this::removeItem);
        detail_btn.setOnAction(event -> showDetailItem());
    }

    public void setData(Item item) {
        id_item.setText(item.getItemId());
        name_item.setText(item.getNameItem());
        quantity_item.setText(String.valueOf(item.getQuantity()));
        role_item = item.getRoleItem();
        origin_item = item.getOrigin();
        img_item = item.getImg();
        des_item = item.getDesItem();
    }

    public void updateItem() {
        try{
            Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM Item where ItemId = ?" ;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id_item.getText());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String itemId = resultSet.getString("ItemId");
                String roleItem = resultSet.getString("RoleItem");
                String nameItem = resultSet.getString("NameItem");
                String origin = resultSet.getString("Origin");
                int quantity = resultSet.getInt("Quantity");
                String img = resultSet.getString("Img");

                String desItem = resultSet.getString("DesItem");

                Item item = new Item(itemId, roleItem, nameItem, origin, quantity, img, desItem);

                Data.getDataGLobal.dataGlobal.setCurrentEditItem(item);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageItem/UpdateItem.fxml"));
            Stage createUserStage = new Stage();
            createStage(loader, createUserStage);
            createUserStage.setOnHiding(event -> {
                updateData();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showDetailItem () {
        try{
            Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM Item where ItemId = ?" ;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id_item.getText());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String itemId = resultSet.getString("ItemId");
                String roleItem = resultSet.getString("RoleItem");
                String nameItem = resultSet.getString("NameItem");
                String origin = resultSet.getString("Origin");
                int quantity = resultSet.getInt("Quantity");
                String img = resultSet.getString("Img");

                String desItem = resultSet.getString("DesItem");

                Item item = new Item(itemId, roleItem, nameItem, origin, quantity, img, desItem);

                Data.getDataGLobal.dataGlobal.setCurrentEditItem(item);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageItem/DetailItem.fxml"));
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
        Item item = Data.getDataGLobal.dataGlobal.getCurentEditItem();

        id_item.setText(item.getItemId());
        name_item.setText(item.getNameItem());
        quantity_item.setText(String.valueOf(item.getQuantity()));
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

    private void removeItem(ActionEvent event) {
        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "DELETE FROM Item WHERE ItemId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, id_item.getText());
                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BorderPane borderPane = (BorderPane) stage.getScene().getRoot();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/Admin/ManageItem/ManageItem.fxml"));
        Parent viewBottomClient;
        try {
            viewBottomClient = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        borderPane.setCenter(viewBottomClient);

    }

//    private void openDetailItem() {
//        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent event) {
//
//                //Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.OTHER);
//
//                Item item = new Item(id_item.getText(), role_item, name_item.getText(), origin_item, Integer.parseInt(quantity_item.getText()), img_item, des_item);
//                Data.getDataGLobal.dataGlobal.setCurrentEditItem(item);
//
//
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                BorderPane borderPane = (BorderPane) stage.getScene().getRoot();
//
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/Fxml/Admin/DetailStore/DetailStore.fxml"));
//
//                Parent viewBottomClient;
//                try {
//                    viewBottomClient = loader.load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                borderPane.setCenter(viewBottomClient);
//            }
//        });
//    }
}
