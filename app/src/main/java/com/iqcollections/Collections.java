package com.iqcollections;

public class Collections {
    String colName;
    String colDescription;
    String colImgUrl;

    public Collections(String colName, String colDescription,String colImgUrl) {
        this.colName = colName;
        this.colDescription = colDescription;
        this.colImgUrl = colImgUrl;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColDescription() {
        return colDescription;
    }

    public void setColDescription(String colDescription) {
        this.colDescription = colDescription;
    }

    public String getColImgUrl() {
        return colImgUrl;
    }

    public void setColImgUrl(String colImgUrl) {
        this.colImgUrl = colImgUrl;
    }
}
