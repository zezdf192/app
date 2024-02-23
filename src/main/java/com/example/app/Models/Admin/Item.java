package com.example.app.Models.Admin;

public class Item {
    private String ItemId;
    private String RoleItem;
    private String NameItem;
    private String Origin;

    private String Img;

    private String DesItem;

    public Item(String itemId, String roleItem, String nameItem, String origin,  String img,String desItem) {
        ItemId = itemId;
        RoleItem = roleItem;
        NameItem = nameItem;
        Origin = origin;
        Img = img;
        DesItem = desItem;
    }

    public Item() {

    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getRoleItem() {
        return RoleItem;
    }

    public void setRoleItem(String roleItem) {
        RoleItem = roleItem;
    }

    public String getNameItem() {
        return NameItem;
    }

    public void setNameItem(String nameItem) {
        NameItem = nameItem;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }


    public String getDesItem() {
        return DesItem;
    }

    public void setDesItem(String desItem) {
        DesItem = desItem;
    }

    @Override
    public String toString() {
        return getItemId();
    }
}
