package Dao;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by ASUS on 2017/3/21.
 */
public class RegisterDao {
    static Connection conn ;
    static {
        conn = DbUtils.getConnection();
        System.out.println("Database Connected");
    }
    private static String sql = "INSERT INTO `sework`.`user` (`name`, `pwd`, `money`) VALUES (?, ?, '0')";
    public static JSONObject registerDao(JSONObject jsonIn) {
        int rs;
        JSONObject ret = new JSONObject();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,jsonIn.getString("username"));
            preparedStatement.setString(2,jsonIn.getString("password"));
            rs = preparedStatement.executeUpdate();
            ret.put("return", rs==1?true:false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
