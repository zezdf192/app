package com.example.app.Models;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Admin.DetailIEModel;
import com.example.app.Models.Admin.Item;
import com.example.app.Models.Admin.ItemStore;
import com.example.app.Models.User.IE;
import com.example.app.Models.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private ItemStore currentEditStore;

    private User currentUser;

    private Item currentEditItem;
    private List<DetailIEModel>  currentDetailIE;

    private IE currentIE;

    public Data() {
        currentEditStore = new ItemStore();
        currentDetailIE = new ArrayList<>();
        currentEditItem = new Item();
        currentUser = new User();
        currentIE = new IE();
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
                DetailIEModel detailIE = new DetailIEModel("", itemId, 0);
                currentDetailIE.add(detailIE);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStore getCurrentEditStore() {
        return currentEditStore;
    }

    public Item getCurentEditItem() {return currentEditItem;}

    public void setCurrentEditItem(Item currentEditItem) {
        this.currentEditItem = currentEditItem;
    }

    public void setCurrentEditStore(ItemStore currentEditStore) {
        this.currentEditStore = currentEditStore;
    }

    public List<DetailIEModel> getCurrentDetailIE() {
        return currentDetailIE;
    }

    public void setCurrentDetailIE(List<DetailIEModel> currentDetailIE) {
        this.currentDetailIE = currentDetailIE;
    }

    public IE getCurrentIE() {
        return currentIE;
    }

    public void setCurrentIE(IE currentIE) {
        this.currentIE = currentIE;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public class getDataGLobal {
        public static Data dataGlobal = new Data();
    }

}
