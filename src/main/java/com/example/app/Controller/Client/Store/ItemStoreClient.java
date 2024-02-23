package com.example.app.Controller.Client.Store;

import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.Data;
import com.example.app.Models.Model;
import com.example.app.Views.ClientMenuOptions;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemStoreClient implements Initializable {
    public HBox hbox;
    public Label storeId;
    public Label name;
    public Label address;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openDetailStore();
    }

    public void setData(ItemStore store) {
        storeId.setText(store.getStoreId());
        name.setText(store.getNameStore());
        address.setText(store.getAddressStore());
    }

    private void openDetailStore() {
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Model.getInstance().getViewClientFactory().getClientSelectedMenuItem().set(ClientMenuOptions.OTHER);

                ItemStore itemStore = new ItemStore(storeId.getText(), name.getText(), address.getText());
                Data.getDataGLobal.dataGlobal.setCurrentEditStore(itemStore);


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                BorderPane borderPane = (BorderPane) stage.getScene().getRoot();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Fxml/Client/Store/DetailStoreClient/DetailStoreClient.fxml"));

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
