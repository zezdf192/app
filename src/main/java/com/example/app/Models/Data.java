package com.example.app.Models;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.DetailIE;
import com.example.app.Models.Admin.Item;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private ItemStore currentEditStore;
    private List<DetailIE>  currentDetailIE;

    public Data() {
        currentEditStore = new ItemStore();
        currentDetailIE = new ArrayList<>();
        getAllItem();
    }

    private void getAllItem() {
        try{
            Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM Item";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                //  String origin, int quantity, String img, float price, String desItem
                String itemId = resultSet.getString("ItemId");
                DetailIE detailIE = new DetailIE("", itemId, 0);
                currentDetailIE.add(detailIE);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStore getCurrentEditStore() {
        return currentEditStore;
    }

    public void setCurrentEditStore(ItemStore currentEditStore) {
        this.currentEditStore = currentEditStore;
    }

    public List<DetailIE> getCurrentDetailIE() {
        return currentDetailIE;
    }

    public void setCurrentDetailIE(List<DetailIE> currentDetailIE) {
        this.currentDetailIE = currentDetailIE;
    }

    public class getDataGLobal {
        public static Data dataGlobal = new Data();
    }

}
