package Dao;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by ASUS on 2017/3/21.
 */
public class LoginDao {
    static Connection conn ;
    static {
        conn = DbUtils.getConnection();
        System.out.println("Database Connected");
    }
    private static String sql = "SELECT * FROM user WHERE name = ? AND pwd = ?;";
    public static JSONObject loginDao(JSONObject jsonIn) {
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,jsonIn.getString("username"));
            preparedStatement.setString(2,jsonIn.getString("password"));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject ret = new JSONObject();
        ret.put("return", flag);
        return ret;
    }
}
