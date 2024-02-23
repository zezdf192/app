package com.example.app.Controller.Admin.Censorship;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Controller.Admin.DetailStore.ItemInAdd;

import com.example.app.Models.Admin.DetailIEModel;
import com.example.app.Models.Data;
import com.example.app.Models.User.IE;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailIE implements Initializable {

    
    public VBox vbox;
    public Label IEId;
    public Label storeId;
    public Label personId;
    public Label timeIE;
    public Label typeIE;
    public Label roleIE;
    public Button yes_btn1;
    public Button not_btn;

    private List<DetailIEModel> listDetailIE = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateInfoIE();
        getItem();
        renderListItem();
        yes_btn1.setOnAction(event -> updateInforIE(event));
        not_btn.setOnAction(event -> noCensorship(event));
    }

    private void updateInfoIE() {
        IE ie = Data.getDataGLobal.dataGlobal.getCurrentIE();
        storeId.setText(ie.getStoreId());
        personId.setText(ie.getPersonId());
        timeIE.setText(ie.getTimeIE());
        typeIE.setText(ie.getTypeIE());
        roleIE.setText(ie.getRoleIE());
        IEId.setText(ie.getIEId());
        if(ie.getRoleIE().equals("DA DUYET") || ie.getRoleIE().equals("KHONG DUYET")) {
            yes_btn1.setVisible(false);
            not_btn.setVisible(false);
        }

    }

    private void getItem() {
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "select * from DetailIE join Item on DetailIE.ItemId = Item.ItemId and DetailIE.IEId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, IEId.getText());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String itemId = resultSet.getString("IEId");
                        String nameId = resultSet.getString("NameItem");
                        int quantity = resultSet.getInt("Quantity");
                        if (quantity < 0) {
                            quantity = Math.abs(quantity);
                        }
                        DetailIEModel detailIEModel = new DetailIEModel(itemId, nameId, quantity);
                        listDetailIE.add(detailIEModel);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderListItem() {
        for (int i = 0; i < listDetailIE.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Admin/Censorship/ItemVatTu.fxml"));

            try {
                AnchorPane hBox = fxmlLoader.load();
                ItemVatTuController cic = fxmlLoader.getController();
                cic.setData(listDetailIE.get(i));
                vbox.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void noCensorship(ActionEvent event) {
        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "UPDATE IE SET RoleIE = ? WHERE IEId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "C3");
                preparedStatement.setString(2, IEId.getText());

                int rowsAffected = preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        IE ie = new IE(IEId.getText(), timeIE.getText(), personId.getText(), storeId.getText(), "KHONG DUYET", typeIE.getText());
        Data.getDataGLobal.dataGlobal.setCurrentIE(ie);
        Stage stage = (Stage) yes_btn1.getScene().getWindow();
        stage.close();
    }

    private void updateInforIE(ActionEvent event) {
        try (Connection connection = ConnectDB.getConnection()) {
            String sql = "UPDATE IE SET RoleIE = ? WHERE IEId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "C1");
                preparedStatement.setString(2, IEId.getText());

                int rowsAffected = preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        IE ie = new IE(IEId.getText(), timeIE.getText(), personId.getText(), storeId.getText(), "DA DUYET", typeIE.getText());
        Data.getDataGLobal.dataGlobal.setCurrentIE(ie);
        Stage stage = (Stage) yes_btn1.getScene().getWindow();
        stage.close();
    }
}
