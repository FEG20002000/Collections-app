package com.iqcollections;

public class Collections {
    String colName;
    String colDescription;

    public Collections(String colName, String colDescription) {
        this.colName = colName;
        this.colDescription = colDescription;
    }

    public String getColName() {
        return colName;
    }

    public String getColDescription() {
        return colDescription;
    }
}
