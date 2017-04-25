package Dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by ASUS on 2017/3/21.
 */
public class DbUtils   {
    private static String serverName = "localhost";
    private static String database = "sework";
    private static String url = "jdbc:mysql://" + serverName + "/" + database + "?&useSSL=true";
    private static Connection conn = null;
    public static Connection getConnection(){
        try {
            conn= DriverManager.getConnection(url, "root", "jathen");
        } catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
