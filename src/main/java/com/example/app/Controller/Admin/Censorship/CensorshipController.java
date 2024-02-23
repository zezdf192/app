package com.example.app.Controller.Admin.Censorship;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Controller.Client.Check.CheckItemController;
import com.example.app.Models.User.IE;
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

public class CensorshipController implements Initializable {
    public VBox vbox;
    public TextField text_find;
    public ComboBox find_comboBox;
    public Button find_btn;

    private List<IE> listIE = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> role = FXCollections.observableArrayList(
                "Tìm kiếm theo mã đơn hàng", "Tìm kiếm theo mã kho", "Tìm kiếm theo thời gian (YYYY-MM-DD)" ,"Tìm kiếm theo trạng thái"
        );

        find_comboBox.setItems(role);
        find_comboBox.setValue("Tìm kiếm theo mã đơn hàng");

        callAPI();
        renderListIE();
        find_btn.setOnAction(event -> findData());
    }

    public void callAPI() {
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "select * from IE join RoleCode on IE.RoleIE = RoleCode.KeyCode and PersonId = ?";
            //String IEId, String timeIE, String personId, String storeId, String roleIE
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "P1");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String IEId = resultSet.getString("IEId");
                        String timeIE = resultSet.getString("TimeIE");
                        String personId = resultSet.getString("PersonId");
                        String storeId = resultSet.getString("StoreId");
                        String roleIE = resultSet.getString("ValueRole");
                        String typeIE = resultSet.getString("TypeIE");
                        IE ie = new IE(IEId, timeIE, personId, storeId, roleIE, typeIE);
                        listIE.add(ie);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderListIE() {
        for (int i = 0; i < listIE.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Admin/Censorship/CensorshipItem.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                CensorshipItemController cic = fxmlLoader.getController();
                cic.setData(listIE.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String getTypeFind() {

        String typeFind;
        if (find_comboBox.getValue().equals("Tìm kiếm theo mã kho")) {
            typeFind = "StoreId";
        } else if (find_comboBox.getValue().equals("Tìm kiếm theo thời gian (YYYY-MM-DD)")) {
            typeFind = "TimeIE";
        } else if (find_comboBox.getValue().equals("Tìm kiếm theo trạng thái")) {
            typeFind = "ValueRole";
        }else {
            typeFind = "IEid";
        }
        return typeFind;
    }

    private void findData() {
        String typeFind = getTypeFind();

        try {
            String text = text_find.getText();
            String query = "select * from IE join RoleCode on IE.RoleIE = RoleCode.KeyCode and PersonId = ? " +
                    "and " + typeFind + " LIKE ? " ;

            Connection connection = ConnectDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "P1");
            preparedStatement.setString(2, "%" + text + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            listIE.clear();

            while (resultSet.next()) {
                String IEId = resultSet.getString("IEId");
                String timeIE = resultSet.getString("TimeIE");
                String personId = resultSet.getString("PersonId");
                String storeId = resultSet.getString("StoreId");
                String roleIE = resultSet.getString("ValueRole");
                String typeIE = resultSet.getString("TypeIE");
                IE ie = new IE(IEId, timeIE, personId, storeId, roleIE, typeIE);
                listIE.add(ie);

            }
            vbox.getChildren().clear();
            renderListIE();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
