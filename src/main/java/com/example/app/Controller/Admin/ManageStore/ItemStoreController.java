package com.example.app.Controller.Admin.ManageStore;


import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import com.example.app.Models.Model;
import com.example.app.Views.ClientMenuOptions;
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
import java.util.ResourceBundle;

public class ItemStoreController implements Initializable {
    public Label name;
    public Label address;
    public Button update_btn;
    public Button remove_btn;
    public Label storeId;
    public HBox hbox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update_btn.setOnAction(event -> updateStore());
        remove_btn.setOnAction(event -> removeStore(event));

        openDetailStore();
    }

    public void setData(ItemStore store) {
        storeId.setText(store.getStoreId());
        name.setText(store.getNameStore());
        address.setText(store.getAddressStore());
    }

    public void updateStore() {
        ItemStore store = new ItemStore(storeId.getText(), name.getText(), address.getText());
        Data.getDataGLobal.dataGlobal.setCurrentEditStore(store);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageStore/UpdateStore.fxml"));
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
        ItemStore store = Data.getDataGLobal.dataGlobal.getCurrentEditStore();
        name.setText(store.getNameStore());
        address.setText(store.getAddressStore());
        storeId.setText(store.getStoreId());

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

    private void removeStore(ActionEvent event) {
        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "DELETE FROM Store WHERE StoreId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, storeId.getText());
                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BorderPane borderPane = (BorderPane) stage.getScene().getRoot();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/Admin/ManageStore/ManageStore.fxml"));
        Parent viewBottomClient;
        try {
            viewBottomClient = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        borderPane.setCenter(viewBottomClient);

    }

    private void openDetailStore() {
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                //Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.OTHER);

                ItemStore itemStore = new ItemStore(storeId.getText(), name.getText(), address.getText());
                Data.getDataGLobal.dataGlobal.setCurrentEditStore(itemStore);


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                BorderPane borderPane = (BorderPane) stage.getScene().getRoot();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Fxml/Admin/DetailStore/DetailStore.fxml"));

                Parent viewBottomClient;
                try {
                    viewBottomClient = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                borderPane.setCenter(viewBottomClient);
            }
        });
    }
}
