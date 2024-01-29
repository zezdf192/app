package com.example.app.Models.Admin;

public class ItemStore {
    private String storeId;
    private String nameStore;

    private String addressStore;

    public ItemStore(String storeId, String nameStore, String addressStore) {
        this.storeId = storeId;
        this.nameStore = nameStore;
        this.addressStore = addressStore;
    }
    public ItemStore() {

    }


    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getAddressStore() {
        return addressStore;
    }

    public void setAddressStore(String addressStore) {
        this.addressStore = addressStore;
    }
}
