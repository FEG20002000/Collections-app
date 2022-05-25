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

    public String getColDescription() {
        return colDescription;
    }
}
