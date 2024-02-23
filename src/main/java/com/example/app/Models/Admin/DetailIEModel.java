package com.example.app.Models.Admin;

public class DetailIEModel {
    private String IEId;
    private String ItemId;

    private int quantity;

    public DetailIEModel(String IEId, String itemId, int quantity) {
        this.IEId = IEId;
        ItemId = itemId;
        this.quantity = quantity;
    }

    public DetailIEModel() {}

    public String getIEId() {
        return IEId;
    }

    public void setIEId(String IEId) {
        this.IEId = IEId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
