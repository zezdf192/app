package com.example.app.Models.User;

public class IE {
    private String IEId;
    private String timeIE;
    private String personId;
    private String storeId;
    private String roleIE;
    private String typeIE;

    public IE(String IEId, String timeIE, String personId, String storeId, String roleIE, String typeIE) {
        this.IEId = IEId;
        this.timeIE = timeIE;
        this.personId = personId;
        this.storeId = storeId;
        this.roleIE = roleIE;
        this.typeIE = typeIE;
    }

    public IE() {

    }

    public String getIEId() {
        return IEId;
    }

    public void setIEId(String IEId) {
        this.IEId = IEId;
    }

    public String getTimeIE() {
        return timeIE;
    }

    public void setTimeIE(String timeIE) {
        this.timeIE = timeIE;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRoleIE() {
        return roleIE;
    }

    public void setRoleIE(String roleIE) {
        roleIE = roleIE;
    }

    public String getTypeIE() {
        return typeIE;
    }

    public void setTypeIE(String typeIE) {
        this.typeIE = typeIE;
    }
}
