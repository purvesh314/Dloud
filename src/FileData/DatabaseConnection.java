/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileData;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author purvesh
 */
public class DatabaseConnection {

    static String url = "jdbc:mysql://127.0.0.1:3306/";
    static String dbName = "te3157db";
    static String driver = "com.mysql.jdbc.Driver";
    static String username = "te3157";
    static String password = "te3157";
    static Connection conn;
    static Statement st = null;

    public static String getUrl() {
        return url;
    }

    public static String getDbName() {
        return dbName;
    }

    public static String getDriver() {
        return driver;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static Connection getConn() {
        return conn;
    }

    public static Statement getSt() {
        return st;
    }

    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(url + dbName, username, password);
    }

    public void uploadData(String filename) throws ClassNotFoundException, SQLException {
        getConnection();
        try (PreparedStatement ps = conn.prepareStatement("insert into dloud values(?,'me')")) {
            ps.setString(1, filename);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

}
