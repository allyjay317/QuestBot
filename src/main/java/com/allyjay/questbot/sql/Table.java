package com.allyjay.questbot.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Attribute> attributes;
    private String name;
    public static Table getTable(String tableName, ResultSetMetaData metaData) throws SQLException {
        Table t = new Table();
        t.name = tableName;
        t.attributes = new ArrayList<>();
        int numCols = metaData.getColumnCount();
        for(int i=1; i<= numCols; i++){
            String name = metaData.getColumnName(i);
            Type type = Type.valueOf(metaData.getColumnTypeName(i).toUpperCase());
        }

        return t;
    }
}
