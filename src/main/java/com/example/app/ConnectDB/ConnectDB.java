package com.example.app.ConnectDB;

import java.sql.*;

public class ConnectDB {
    private static final String JDBC_URL = "jdbc:sqlserver://DESKTOP-V09UCFU\\SQLEXPRESS:1433;databaseName=QLKH;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {

            throw new RuntimeException(e);
        }
        return conn;
    }



}
