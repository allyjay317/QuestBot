package com.allyjay.questbot.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String database;
    private List<Table> tables = new ArrayList<>();
    private Connection connection;

    public Database(String filename)throws SQLException{
        database = filename;
        connect();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(connection.getCatalog(), null, "%", null);
        Statement statement = connection.createStatement();
        if(!resultSet.next()){
            return;
        }else do{
            String tableName = resultSet.getString(3);
            ResultSet tableRS = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = tableRS.getMetaData();
            Table.getTable(tableName, rsmd);

            tableRS.close();

        }while(resultSet.next());

        statement.close();
        connection.commit();
        connection.close();
    }
    private void connect() throws SQLException{

        connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        connection.setAutoCommit(false);
    }
}
